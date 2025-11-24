package nl.uwv.smz.diamond.view.routing

import io.github.oshai.kotlinlogging.KotlinLogging.logger
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.request.httpMethod
import io.ktor.server.request.uri
import io.ktor.server.response.respond
import nl.uwv.smz.diamond.domain_failure.ErrorCode
import nl.uwv.smz.diamond.view.model.ApiErrorDto

private val log = logger {}

fun Application.installExceptionHandling() {
    install(StatusPages) {
        // TODO they often do Throwable here :-/ not comfortable with it, but ok...
        exception<Throwable> { call, cause ->
            log.error(cause) {
                "Unhandled exception was thrown: ${call.request.httpMethod} ${call.request.uri}"
            } // TODO test for log
            call.respond(
                status = HttpStatusCode.InternalServerError,
                message = ApiErrorDto(
                    code = ErrorCode.INTERNAL_ERROR.renderedValue,
                    message = cause.message ?: "N/A",
                )
            )
        }
    }
}
