package nl.uwv.smz.diamond.view

import io.github.oshai.kotlinlogging.KotlinLogging.logger
import io.ktor.server.application.Application
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

private val log = logger {}

fun Application.installRouting() {
    log.info { "Installing routing..." }
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        // FIXME write test
    }
}
