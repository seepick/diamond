package nl.uwv.smz.diamond.view.routing

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import nl.uwv.smz.diamond.view.model.ApiErrorDto

class ExceptionHandlingTest : StringSpec({

    val exceptionWithMessage = Exception("exception message")
    val exceptionWithoutMessage = Exception()
    val throwable = Throwable("throwable message")
    val error = Error("error message")

    fun exceptionTest(routeBehavior: () -> Unit, expected: suspend (HttpResponse) -> Unit) {
        viewTest(
            additionalApplicationSetup = {
                routing {
                    get("/testException") {
                        routeBehavior()
                    }
                }
            },
            testCode = { client ->
                val response = client.get("/testException")
                expected(response)
            })
    }
    "When Exception is thrown Then respond with a 500 error" {
        exceptionTest({
            throw exceptionWithMessage
        }, { response ->
            response.status shouldBeEqual HttpStatusCode.InternalServerError
            response.readBody<ApiErrorDto>() shouldBeEqual ApiErrorDto(code = "INTERNAL_ERROR", message = exceptionWithMessage.message!!)
        })
    }
    "When Exception without message is thrown Then render 'N/A' message" {
        exceptionTest({
            throw exceptionWithoutMessage
        }, { response ->
            response.readBody<ApiErrorDto>().message shouldBeEqual "N/A"
        })
    }
    "When Throwable is thrown Then respond with a 500 error" {
        exceptionTest({
            throw throwable
        }, { response ->
            response.status shouldBeEqual HttpStatusCode.InternalServerError
            response.readBody<ApiErrorDto>() shouldBeEqual ApiErrorDto(code = "INTERNAL_ERROR", message = throwable.message!!)
        })
    }
    "When Error is thrown Then respond with a 500 error" {
        exceptionTest({
            throw error
        }, { response ->
            response.status shouldBeEqual HttpStatusCode.InternalServerError
            response.readBody<ApiErrorDto>() shouldBeEqual ApiErrorDto(code = "INTERNAL_ERROR", message = error.message!!)
        })
    }
})
