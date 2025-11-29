package nl.uwv.smz.diamond.view.routing

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import nl.uwv.smz.diamond.domainFailure.ErrorCode
import nl.uwv.smz.diamond.view.model.ApiErrorDto

@Suppress("TooGenericExceptionThrown")
class ExceptionHandlingTest : StringSpec({
    fun givenEndpointBehaviorThenExpect500AndApiErrorDto(routeBehavior: () -> Unit) {
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
                response.status shouldBeEqual HttpStatusCode.InternalServerError
                response.readBody<ApiErrorDto>() shouldBeEqual ApiErrorDto(
                    code = ErrorCode.INTERNAL_ERROR,
                    message = "Internal error occured (please tell admin to check logs)",
                )
            },
        )
    }
    "When Exception is thrown Then respond with a 500 error" {
        givenEndpointBehaviorThenExpect500AndApiErrorDto {
            throw Exception("secret")
        }
    }
    "When Throwable is thrown Then respond with a 500 error" {
        givenEndpointBehaviorThenExpect500AndApiErrorDto {
            throw Throwable("secret")
        }
    }
    "When Error is thrown Then respond with a 500 error" {
        givenEndpointBehaviorThenExpect500AndApiErrorDto {
            throw Error("secret")
        }
    }
})
