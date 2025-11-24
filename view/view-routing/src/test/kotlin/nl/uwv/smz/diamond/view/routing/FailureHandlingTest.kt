package nl.uwv.smz.diamond.view.routing

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.string.shouldBeEmpty
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import nl.uwv.smz.diamond.domain_failure.Failure
import nl.uwv.smz.diamond.view.model.ApiErrorDto

// must be outside, as local inlined functions are not (yet) supported by Kotlin
private inline fun <reified T : Any> failureTest(
    value: Either<Failure, T>,
    crossinline expect: suspend (HttpResponse) -> Unit,
) {
    viewTest(
        additionalApplicationSetup = {
            routing {
                get("/testFailure") {
                    call.handle<T>(value)
                }
            }
        }) { client ->
        val response = client.get("/testFailure")
        expect(response)
    }
}

class FailureHandlingTest : DescribeSpec({
    val someMessage = "some test message"

    describe("When succeed") {
        it("Given Unit Then OK and empty body") {
            failureTest(Unit.right()) { response ->
                response.status shouldBeEqual HttpStatusCode.OK
                response.contentType().shouldBeNull()
                response.bodyAsText().shouldBeEmpty()
            }
        }
        it("Given String Then OK and plain text") {
            failureTest(someMessage.right()) { response ->
                response.status shouldBeEqual HttpStatusCode.OK
                response.contentType().shouldNotBeNull().withoutParameters() shouldBeEqual ContentType.Text.Plain
                response.bodyAsText() shouldBeEqual someMessage
            }
        }
    }
    describe("When fail") {
        data class Case(val failure: Failure, val statusCode: HttpStatusCode)
        listOf(
            // TODO how to enforce exhaustive list of subtypes for sealed type?
            Case(Failure.NotFoundFailure(someMessage), HttpStatusCode.NotFound),
            Case(Failure.CorruptDataFailure(someMessage), HttpStatusCode.InternalServerError),
            Case(Failure.BadRequestFailure(someMessage), HttpStatusCode.BadRequest),
        ).forEach { (failure, expectedStatusCode) ->
            it("Given ${failure::class.simpleName} Then ${expectedStatusCode.value} and error DTO") {
                failureTest(failure.left()) { response ->
                    response.status shouldBeEqual expectedStatusCode
                    response.contentType().shouldNotBeNull().withoutParameters() shouldBeEqual ContentType.Application.Json
                    response.readBody<ApiErrorDto>() shouldBeEqual ApiErrorDto(code = failure.code.renderedValue, message = someMessage)
                }
            }
        }
    }
})
