package nl.uwv.smz.diamond.view.controllerImpl

import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.should
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.types.shouldBeInstanceOf
import nl.uwv.smz.diamond.domain.failure.Failure
import nl.uwv.smz.diamond.domain.model.PageRequest
import nl.uwv.smz.diamond.domain.model.build
import nl.uwv.smz.diamond.view.model.PageRequestDto

class PageRequestDtoToPageRequestTest : DescribeSpec({
    val defaultSkip = 0
    val defaultTake = 10

    describe("Sunshine") {
        fun assertDto(given: Pair<String?, String?>, expected: Pair<Int, Int>) {
            PageRequestDto(given.first, given.second).toPageRequest()
                .shouldBeRight() shouldBeEqual PageRequest.build(expected.first, expected.second)
        }
        it("Given nothing Then return default") {
            assertDto(null to null, defaultSkip to defaultTake)
        }
        it("Given valid skip Then return with default take") {
            assertDto("2" to null, 2 to defaultTake)
        }
        it("Given valid take Then return with default skip") {
            assertDto(null to "3", defaultSkip to 3)
        }
        it("Given both valid Then return with both") {
            assertDto("2" to "3", 2 to 3)
        }
    }
    describe("Invalid") {
        fun assertInvalid(given: Pair<String?, String?>, vararg messageParts: String) {
            PageRequestDto(given.first, given.second).toPageRequest().shouldBeLeft().should { failure ->
                failure.shouldBeInstanceOf<Failure.BadRequestFailure>()
                println("[${failure.message}]")
                messageParts.forEach { part ->
                    failure.message shouldContain part
                }
            }
        }
        it("Given skip not an integer Then fail") {
            assertInvalid("foo" to null, "skip", "foo")
        }
        it("Given take not an integer Then fail") {
            assertInvalid(null to "foo", "take", "foo")
        }
        it("Given skip out of range Then fail") {
            assertInvalid("-1" to null, "skip", "-1")
        }
        it("Given take out of range Then fail") {
            assertInvalid(null to "0", "take", "0")
        }
    }
})
