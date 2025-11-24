package nl.uwv.smz.diamond.view.routing

import arrow.core.Either
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.RoutingCall
import nl.uwv.smz.diamond.domain_failure.Failure
import nl.uwv.smz.diamond.view.model.ApiErrorDto

suspend inline fun <reified RESULT : Any> RoutingCall.handle(result: Either<Failure, RESULT>) {
    result.fold(
        { failure ->
            respond(
                failure.httpStatusCode, ApiErrorDto(
                    code = failure.code,
                    message = failure.message,
                )
            )
        },
        {
            if (RESULT::class != Unit::class) {
                respond(HttpStatusCode.OK, it)
            } else {
                respond(HttpStatusCode.OK)
            }
        }
    )
}

val Failure.httpStatusCode
    get() = when (this) {
        is Failure.BadRequestFailure -> HttpStatusCode.BadRequest
        is Failure.NotFoundFailure -> HttpStatusCode.NotFound
        is Failure.CorruptDataFailure -> HttpStatusCode.InternalServerError
    }
