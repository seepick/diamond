package nl.uwv.smz.diamond.persistence.impl

import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.core.annotation.RequiresTag
import io.kotest.core.spec.DslDrivenSpec
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.core.spec.style.describeSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldBeSingleton
import io.kotest.matchers.collections.shouldContainInOrder
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.should
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.types.shouldBeInstanceOf
import io.kotest.property.Arb
import io.kotest.property.arbitrary.next
import io.kotest.property.arbitrary.uuid
import kotlinx.datetime.LocalDateTime
import nl.uwv.smz.diamond.domain.failure.Failure
import nl.uwv.smz.diamond.domain.model.Crystal
import nl.uwv.smz.diamond.domain.model.CrystalId
import nl.uwv.smz.diamond.domain.model.CrystalSortField
import nl.uwv.smz.diamond.domain.model.CrystalSortRequest
import nl.uwv.smz.diamond.domain.model.CrystalSortingsRequest
import nl.uwv.smz.diamond.domain.model.CrystalUpdate
import nl.uwv.smz.diamond.domain.model.PageRequest
import nl.uwv.smz.diamond.domain.model.SortDirection
import nl.uwv.smz.diamond.domain.model.crystal
import nl.uwv.smz.diamond.domain.model.crystalCreate
import nl.uwv.smz.diamond.domain.model.crystalId
import nl.uwv.smz.diamond.domain.model.crystalUpdate
import nl.uwv.smz.diamond.domain.model.default
import nl.uwv.smz.diamond.domain.model.gram
import nl.uwv.smz.diamond.persistence.api.CrystalRepo
import nl.uwv.smz.diamond.persistence.impl.testInfra.DbListener
import nl.uwv.smz.diamond.persistence.impl.testInfra.InmemoryDbListener
import nl.uwv.smz.diamond.persistence.impl.testInfra.TestcontainersDbListener
import nl.uwv.smz.diamond.shared.common.StaticClock
import nl.uwv.smz.diamond.shared.common.StaticUuidGenerator
import nl.uwv.smz.diamond.shared.common.now
import nl.uwv.smz.diamond.shared.test.KoTags
import nl.uwv.smz.diamond.shared.test.kotlinLocalDateTime
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.uuid.Uuid
import kotlin.uuid.toJavaUuid
import kotlin.uuid.toKotlinUuid

class CrystalExposedDboRepoInmemoryTest : DescribeSpec({
    configureRepoTests(InmemoryDbListener())
//    val dbListener = InmemoryDbListener()
//    extension(dbListener)
//    include(
//        crystalRepoTest(dbListener) { db, uuid, now ->
//            CrystalExposedDboRepo(db, StaticUuidGenerator(uuid), StaticClock(now))
//        },
//    )
})

// no dao
// class CrystalExposedDaoRepoInmemoryTest : DescribeSpec({
//    val dbListener = InmemoryDbListener()
//    extension(dbListener)
//    include(crystalRepoTest(dbListener, { CrystalExposedDaoRepo(it) }))
// })

@RequiresTag(KoTags.testcontainersName)
class CrystalExposedDboRepoTestcontainersTest : DescribeSpec({
    configureRepoTests(TestcontainersDbListener())
//    val dbListener = TestcontainersDbListener()
//    extension(dbListener)
//    include(crystalRepoTest(dbListener) { db, uuid, now ->
//        CrystalExposedDboRepo(db, StaticUuidGenerator(uuid), StaticClock(now))
//    }
//    )
})

private fun DslDrivenSpec.configureRepoTests(dbListener: DbListener) {
    extension(dbListener)
    include(
        crystalRepoTest(dbListener) { db, uuid, now ->
            CrystalExposedDboRepo(db, StaticUuidGenerator(uuid), StaticClock(now))
        },
    )
}

fun CrystalSortingsRequest.Companion.empty() = CrystalSortingsRequest(emptyList())

fun sort(vararg fieldsAndDirs: Pair<CrystalSortField, SortDirection>) = CrystalSortingsRequest(
    fieldsAndDirs.map { CrystalSortRequest(it.first, it.second) },
)

@Suppress("LongMethod")
fun crystalRepoTest(
    dbListener: DbListener,
    repoProvider: (Database, Uuid, LocalDateTime) -> CrystalRepo,
) = describeSpec {
    // extension(dbListener) ... won't pick-up runtime interface types :-/
    fun repo(
        uuid: Uuid = Uuid.random(),
        now: LocalDateTime = LocalDateTime.now(),
    ) = repoProvider(dbListener.db, uuid, now)

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
            it[CrystalTable.created] = crystal.created
            it[CrystalTable.weightInGrams] = crystal.weight.value
        }
    }

    include(
        paginationRepoTests(
            dbProvider = { dbListener.db },
            repoProvider = repoProvider,
            inserter = { repeat(it) { insert(Arb.crystal().next()) } },
            paginatedRepoCall = { selectAll(it, CrystalSortingsRequest.empty()) },
        ),
    )
    describe("Sorting") {
        it("Simple asc") {
            insert(crystal1.copy(weight = 1.gram))
            insert(crystal2.copy(weight = 2.gram))

            repo().selectAll(PageRequest.default(), sort(CrystalSortField.WeightInGram to SortDirection.Asc))
                .shouldBeRight().map { it.weight.value } shouldContainInOrder listOf(1, 2)
        }
        it("Simple desc") {
            insert(crystal1.copy(weight = 1.gram))
            insert(crystal2.copy(weight = 2.gram))

            repo().selectAll(PageRequest.default(), sort(CrystalSortField.WeightInGram to SortDirection.Desc))
                .shouldBeRight().map { it.weight.value } shouldContainInOrder listOf(2, 1)
        }
        // TODO test to sort for multiple fields
    }

    describe("select all") {
        it("Given nothing Then return empty") {
            repo().selectAll(PageRequest.default(), CrystalSortingsRequest.empty()).shouldBeRight().shouldBeEmpty()
        }
        it("Given single Then return it") {
            insert(crystal)

            repo().selectAll(PageRequest.default(), CrystalSortingsRequest.empty())
                .shouldBeRight().shouldBeSingleton().first() shouldBeEqual crystal
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
            val uuid = Arb.uuid().next().toKotlinUuid()
            val now = Arb.kotlinLocalDateTime().next()
            val actual = repo(now = now, uuid = uuid).insert(create).shouldBeRight()
            actual shouldBeEqual Crystal(
                id = CrystalId(uuid),
                created = now,
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
            val update = CrystalUpdate(id = crystal.id, weight = (crystal.weight + 1).shouldBeRight())

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
