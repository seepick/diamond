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
import io.kotest.property.arbitrary.arbitrary
import io.kotest.property.arbitrary.int
import io.kotest.property.arbitrary.next
import io.kotest.property.arbitrary.uuid
import nl.uwv.smz.diamond.domain.model.Crystal
import nl.uwv.smz.diamond.domain.model.CrystalUpdate
import nl.uwv.smz.diamond.domain.model.crystal
import nl.uwv.smz.diamond.domain.model.crystalCreate
import nl.uwv.smz.diamond.domain.model.crystalId
import nl.uwv.smz.diamond.domain.model.crystalUpdate
import nl.uwv.smz.diamond.domainFailure.Failure
import nl.uwv.smz.diamond.persistence.api.CrystalDbo
import nl.uwv.smz.diamond.persistence.api.CrystalRepo
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.uuid.toJavaUuid
import kotlin.uuid.toKotlinUuid

@Suppress("LongMethod")
fun crystalRepoTest(provideRepo: (Database) -> CrystalRepo) = describeSpec {
    val dbListener = DbListener()
    extension(dbListener)
    fun repo() = provideRepo(dbListener.db)

    val id = Arb.crystalId().next()
    val crystal = Arb.crystal().next()
    val crystal1 = Arb.crystal().next()
    val crystal2 = Arb.crystal().next()
    val create = Arb.crystalCreate().next()
    val update = Arb.crystalUpdate().next()

    fun selectAll() = transaction { CrystalTable.selectAll() }
    fun insert(crystal: Crystal) = transaction {
        CrystalTable.insert {
            it[CrystalTable.id] = crystal.id.value.toJavaUuid()
            it[CrystalTable.weightInGrams] = crystal.weight.value
        }
    }

    describe("select all") {
        it("Given nothing Then return empty") {
            repo().selectAll().shouldBeRight().shouldBeEmpty()
        }
        it("Given single Then return it") {
            insert(crystal)

            repo().selectAll().shouldBeRight().shouldBeSingleton().first() shouldBeEqual crystal
        }
    }
    describe("select by ID") {
        it("Given nothing Then fail not found") {
            repo().selectById(id).shouldBeLeft().shouldBeInstanceOf<Failure.NotFoundFailure>().message shouldContain id.toString()
        }
        it("Given it Then return it") {
            insert(crystal)

            repo().selectById(crystal.id).shouldBeRight(crystal)
        }
    }
    describe("create") {
        it("Given nothing Then created") {
            val actual = repo().create(create).shouldBeRight()
            actual shouldBeEqual Crystal(
                id = actual.id,
                weight = create.weight,
            )

            selectAll().shouldBeSingleton().first().should {
                it[CrystalTable.id] shouldBeEqual actual.id
                it[CrystalTable.weightInGrams] shouldBeEqual actual.weight.value
            }
        }
    }
    describe("update") {
        it("Given nothing Then fail not found") {
            repo().update(update).shouldBeLeft().shouldBeInstanceOf<Failure.NotFoundFailure>().message shouldContain update.id.toString()
        }
        it("Given something Then update it") {
            insert(crystal)
            val update = CrystalUpdate(id = crystal.id, weight = crystal.weight + 1)

            repo().update(update).shouldBeRight(crystal.copy(weight = update.weight))

            selectAll().shouldBeSingleton().first().should {
                it[CrystalTable.id] shouldBeEqual crystal.id
                it[CrystalTable.weightInGrams] shouldBeEqual update.weight
            }
        }
    }
    describe("delete") {
        it("Given nothing Then fail not found") {
            repo().delete(id).shouldBeLeft().shouldBeInstanceOf<Failure.NotFoundFailure>().message shouldContain id.toString()
        }
        it("Given it Then it's gone") {
            insert(crystal)

            repo().delete(crystal.id).shouldBeRight()

            selectAll().shouldBeEmpty()
        }
        it("Given two Then only one gone") {
            insert(crystal1)
            insert(crystal2)

            repo().delete(crystal1.id).shouldBeRight()

            selectAll().shouldBeSingleton().first()[CrystalTable.id].value shouldBeEqual crystal2.id.value.toJavaUuid()
        }
    }
}

class CrystalExposedDboRepoTest : DescribeSpec({
    include(crystalRepoTest { CrystalExposedDboRepo(it) })
})

class CrystalExposedDaoRepoTest : DescribeSpec({
    include(crystalRepoTest { CrystalExposedDaoRepo(it) })
})


fun Arb.Companion.crystaleDbo() = arbitrary {
    CrystalDbo(
        id = uuid().bind().toKotlinUuid(),
        weightInGram = int(1..5000).bind(),
    )
}
