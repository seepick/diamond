package nl.uwv.smz.diamond.view

import io.github.oshai.kotlinlogging.KotlinLogging.logger
import io.ktor.server.application.Application
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

private val log = logger {}

fun Application.installDiamondRouting() {
    log.info { "Installing routing..." }
    // TODO @cpi do this todo due to 10.12.2025
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        // FIXME write test
    }
}
