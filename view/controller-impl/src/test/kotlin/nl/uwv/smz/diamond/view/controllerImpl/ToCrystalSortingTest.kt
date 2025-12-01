package nl.uwv.smz.diamond.view.controllerImpl

import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.should
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.types.shouldBeInstanceOf
import nl.uwv.smz.diamond.domain.failure.Failure
import nl.uwv.smz.diamond.domain.model.CrystalSortField
import nl.uwv.smz.diamond.domain.model.CrystalSortRequest
import nl.uwv.smz.diamond.domain.model.CrystalSortingsRequest
import nl.uwv.smz.diamond.domain.model.SortDirection
import nl.uwv.smz.diamond.domain.model.empty

class ToCrystalSortingTest : DescribeSpec({
    val defaultDirection = SortDirection.Asc

    fun assertSorting(string: String, vararg values: Pair<CrystalSortField, SortDirection>) {
        string.toCrystalSorting().shouldBeRight() shouldBeEqual CrystalSortingsRequest(
            values.map { CrystalSortRequest(it.first, it.second) },
        )
    }

    fun assertSortingFailure(string: String, vararg messageParts: String) {
        string.toCrystalSorting().shouldBeLeft().should { failure ->
            failure.shouldBeInstanceOf<Failure.BadRequestFailure>()
            messageParts.forEach { part ->
                failure.message shouldContain part
            }
        }
    }

    describe("Valid default") {
        it("When null Then empty sorting") {
            val nullString: String? = null
            nullString.toCrystalSorting().shouldBeRight() shouldBeEqual CrystalSortingsRequest.empty()
        }
        it("When empty Then empty sorting") {
            "".toCrystalSorting().shouldBeRight() shouldBeEqual CrystalSortingsRequest.empty()
        }
    }
    describe("Valid simple sort") {
        it("When sort by id without direction Then sort by it with default direction") {
            assertSorting(
                "id",
                CrystalSortField.Id to defaultDirection,
            )
        }
        it("When sort by id with ascending direction Then sort by it with ascending direction") {
            assertSorting(
                "+id",
                CrystalSortField.Id to SortDirection.Asc,
            )
        }
        it("When sort by id with descending direction Then sort by it with descending direction") {
            assertSorting(
                "-id",
                CrystalSortField.Id to SortDirection.Desc,
            )
        }
    }
    describe("Valid multiple") {
        it("When sort by two fields Then sort by both with default direction") {
            assertSorting(
                "id,weight",
                CrystalSortField.Id to defaultDirection,
                CrystalSortField.WeightInGram to defaultDirection,
            )
        }
        it("When sort by two fields with direction Then sort by both with their direction") {
            assertSorting(
                "-id,+weight",
                CrystalSortField.Id to SortDirection.Desc,
                CrystalSortField.WeightInGram to SortDirection.Asc,
            )
        }
    }
    describe("Invalid") {
        it("When unknown field Then fail") {
            assertSortingFailure("foo", "foo", "id, weight")
        }
        // nah - would require to properly parse (regexp) the message ;)
//        it("When unknown direction Then fail") {
//            assertSortingFailure("~id", "~", "direction")
//        }
    }
    // TODO dot syntax for nested objects
})
