package nl.uwv.smz.diamond.view.routing

import arrow.core.Either
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.RoutingCall
import nl.uwv.smz.diamond.domain_failure.Failure

suspend inline fun <reified RESULT : Any> RoutingCall.handle(result: Either<Failure, RESULT>) {
    result.fold(
        {
            respond(it.httpStatusCode, "nope") // FIXME ApiError + test first
        },
        {
            respond(it)
        }
    )
}

val Failure.httpStatusCode
    get() = when (this) {
        is Failure.InvalidRequestFailure -> HttpStatusCode.BadRequest
        is Failure.NotFoundFailure -> HttpStatusCode.NotFound
    }
