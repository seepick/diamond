package nl.uwv.smz.diamond.persistence.impl

import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.core.spec.style.describeSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldBeSingleton
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.should
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.types.shouldBeInstanceOf
import io.kotest.property.Arb
import io.kotest.property.arbitrary.next
import nl.uwv.smz.diamond.domain.failure.Failure
import nl.uwv.smz.diamond.domain.model.Crystal
import nl.uwv.smz.diamond.domain.model.CrystalUpdate
import nl.uwv.smz.diamond.domain.model.PageRequest
import nl.uwv.smz.diamond.domain.model.crystal
import nl.uwv.smz.diamond.domain.model.crystalCreate
import nl.uwv.smz.diamond.domain.model.crystalId
import nl.uwv.smz.diamond.domain.model.crystalUpdate
import nl.uwv.smz.diamond.domain.model.default
import nl.uwv.smz.diamond.persistence.api.CrystalRepo
import nl.uwv.smz.diamond.persistence.impl.testInfra.DbListener
import nl.uwv.smz.diamond.persistence.impl.testInfra.InmemoryDbListener
import nl.uwv.smz.diamond.persistence.impl.testInfra.TestcontainersDbListener
import nl.uwv.smz.diamond.shared.test.KoTags
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.uuid.toJavaUuid

class CrystalExposedDboRepoInmemoryTest : DescribeSpec({
    val dbListener = InmemoryDbListener()
    extension(dbListener)
    include(crystalRepoTest(dbListener, { CrystalExposedDboRepo(it) }))
})

class CrystalExposedDaoRepoInmemoryTest : DescribeSpec({
    val dbListener = InmemoryDbListener()
    extension(dbListener)
    include(crystalRepoTest(dbListener, { CrystalExposedDaoRepo(it) }))
})

class CrystalExposedDboRepoTestcontainersTest : DescribeSpec({
    tags(KoTags.testcontainersTag)
    val dbListener = TestcontainersDbListener()
    extension(dbListener)
    include(crystalRepoTest(dbListener, { CrystalExposedDboRepo(it) }))
})

@Suppress("LongMethod")
fun crystalRepoTest(
    dbListener: DbListener,
    repoProvider: (Database) -> CrystalRepo,
) = describeSpec {
    // extension(dbListener) ... won't pick-up runtime interface types :-/
    fun repo() = repoProvider(dbListener.db)

    fun <T> tx(code: Transaction.() -> T): T = transaction(dbListener.db, code)

    val id = Arb.crystalId().next()
    val crystal = Arb.crystal().next()
    val crystal1 = Arb.crystal().next()
    val crystal2 = Arb.crystal().next()
    val create = Arb.crystalCreate().next()
    val update = Arb.crystalUpdate().next()

    fun insert(crystal: Crystal) = tx {
        CrystalTable.insert {
            it[CrystalTable.id] = crystal.id.value.toJavaUuid()
            it[CrystalTable.weightInGrams] = crystal.weight.value
        }
    }

    include(
        paginationRepoTests(
            dbProvider = { dbListener.db },
            repoProvider = repoProvider,
            inserter = { repeat(it) { insert(Arb.crystal().next()) } },
            paginatedRepoCall = { selectAll(it) },
        ),
    )

    describe("select all") {
        it("Given nothing Then return empty") {
            repo().selectAll(PageRequest.default()).shouldBeRight().shouldBeEmpty()
        }
        it("Given single Then return it") {
            insert(crystal)

            repo().selectAll(PageRequest.default()).shouldBeRight().shouldBeSingleton().first() shouldBeEqual crystal
        }
    }
    describe("select by ID") {
        it("Given nothing Then fail not found") {
            repo().selectById(id).shouldBeLeft()
                .shouldBeInstanceOf<Failure.NotFoundFailure>().message shouldContain id.toString()
        }
        it("Given it Then return it") {
            insert(crystal)

            repo().selectById(crystal.id).shouldBeRight(crystal)
        }
    }
    describe("create") {
        it("Given nothing Then created") {
            val actual = repo().insert(create).shouldBeRight()
            actual shouldBeEqual Crystal(
                id = actual.id,
                weight = create.weight,
            )

            tx {
                CrystalTable.selectAll().shouldBeSingleton().first().should {
                    it[CrystalTable.id].value shouldBeEqual actual.id.value.toJavaUuid()
                    it[CrystalTable.weightInGrams] shouldBeEqual actual.weight.value
                }
            }
        }
    }
    describe("update") {
        it("Given nothing Then fail not found") {
            repo().update(update).shouldBeLeft()
                .shouldBeInstanceOf<Failure.NotFoundFailure>().message shouldContain update.id.toString()
        }
        it("Given something Then update it") {
            insert(crystal)
            val update = CrystalUpdate(id = crystal.id, weight = crystal.weight + 1)

            repo().update(update).shouldBeRight(crystal.copy(weight = update.weight))

            tx {
                CrystalTable.selectAll().shouldBeSingleton().first().should {
                    it[CrystalTable.id].value shouldBeEqual crystal.id.value.toJavaUuid()
                    it[CrystalTable.weightInGrams] shouldBeEqual update.weight.value
                }
            }
        }
    }
    describe("delete") {
        it("Given nothing Then fail not found") {
            repo().delete(id).shouldBeLeft()
                .shouldBeInstanceOf<Failure.NotFoundFailure>().message shouldContain id.toString()
        }
        it("Given it Then it's gone") {
            insert(crystal)

            repo().delete(crystal.id).shouldBeRight()

            tx {
                CrystalTable.selectAll().shouldBeEmpty()
            }
        }
        it("Given two Then only one gone") {
            insert(crystal1)
            insert(crystal2)

            repo().delete(crystal1.id).shouldBeRight()

            tx {
                CrystalTable.selectAll().shouldBeSingleton()
                    .first()[CrystalTable.id].value shouldBeEqual crystal2.id.value.toJavaUuid()
            }
        }
    }
}
