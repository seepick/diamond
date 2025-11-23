package nl.uwv.smz.diamond.persistence.impl

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldBeSingleton
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.property.Arb
import io.kotest.property.arbitrary.arbitrary
import io.kotest.property.arbitrary.int
import io.kotest.property.arbitrary.next
import io.kotest.property.arbitrary.uuid
import nl.uwv.smz.diamond.persistence.api.CrystalDbo
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.uuid.toJavaUuid
import kotlin.uuid.toKotlinUuid

class CrystalExposedRepoTest : DescribeSpec({
    extension(DbListener)
    describe("select all") {
        it("Given nothing Then return empty") {
            CrystalExposedRepo.selectAll().shouldBeEmpty()
        }
        it("Given single Then return it") {
            val dbo = Arb.crystaleDbo().next()
            transaction {
                CrystalTable.insert {
                    it[CrystalTable.id] = dbo.id.toJavaUuid()
                    it[CrystalTable.weightInGrams] = dbo.weightInGram
                }
            }
            CrystalExposedRepo.selectAll().shouldBeSingleton().first() shouldBeEqual dbo
        }
    }
    // FIXME finish DB tests
})

fun Arb.Companion.crystaleDbo() = arbitrary {
    CrystalDbo(
        id = uuid().next().toKotlinUuid(),
        weightInGram = int(1..5000).next(),
    )
}
