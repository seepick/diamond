package nl.uwv.smz.diamond.persistence.impl

import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.core.spec.style.describeSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldBeSingleton
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.property.Arb
import io.kotest.property.arbitrary.arbitrary
import io.kotest.property.arbitrary.int
import io.kotest.property.arbitrary.next
import io.kotest.property.arbitrary.uuid
import nl.uwv.smz.diamond.domain.model.crystal
import nl.uwv.smz.diamond.persistence.api.CrystalDbo
import nl.uwv.smz.diamond.persistence.api.CrystalRepo
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.uuid.toJavaUuid
import kotlin.uuid.toKotlinUuid

fun crystalRepoTest(provideRepo: (Database) -> CrystalRepo) = describeSpec {
    val dbListener = DbListener()
    extension(dbListener)
    fun repo() = provideRepo(dbListener.db)

    describe("select all") {
        it("Given nothing Then return empty") {
            repo().selectAll().shouldBeRight().shouldBeEmpty()
        }
        it("Given single Then return it") {
            val crystal = Arb.crystal().next()
            transaction {
                CrystalTable.insert {
                    it[CrystalTable.id] = crystal.id.value.toJavaUuid()
                    it[CrystalTable.weightInGrams] = crystal.weight.value
                }
            }
            repo().selectAll().shouldBeRight().shouldBeSingleton().first() shouldBeEqual crystal
        }
    }
    // FIXME finish DB tests
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
