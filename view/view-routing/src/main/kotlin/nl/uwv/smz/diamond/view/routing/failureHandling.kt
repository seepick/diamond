package nl.uwv.smz.diamond.view.routing

import arrow.core.Either
import io.github.oshai.kotlinlogging.KotlinLogging.logger
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.httpMethod
import io.ktor.server.request.path
import io.ktor.server.response.respond
import io.ktor.server.routing.RoutingCall
import nl.uwv.smz.diamond.domainFailure.Failure
import nl.uwv.smz.diamond.view.model.ApiErrorDto

private val log = logger {}

internal suspend inline fun <reified RESULT : Any> RoutingCall.handle(result: Either<Failure, RESULT>) {
    result.fold(
        { failure ->
            log.error(failure.exception) {
                "FAILED! ${request.httpMethod} ${request.path()} - ${failure.httpStatusCode}/${failure.code.renderedValue}: ${failure.message}"
            }
            respond(
                status = failure.httpStatusCode,
                message =
                    ApiErrorDto(
                        code = failure.code,
                        message = failure.message,
                    ),
            )
        },
        {
            if (RESULT::class != Unit::class) {
                respond(HttpStatusCode.OK, it)
            } else {
                respond(HttpStatusCode.OK)
            }
        },
    )
}

private val Failure.httpStatusCode
    get() =
        when (this) {
            is Failure.BadRequestFailure -> HttpStatusCode.BadRequest
            is Failure.NotFoundFailure -> HttpStatusCode.NotFound
            is Failure.CorruptDataFailure -> HttpStatusCode.InternalServerError
            is Failure.ConnectionError -> HttpStatusCode.InternalServerError
        }
