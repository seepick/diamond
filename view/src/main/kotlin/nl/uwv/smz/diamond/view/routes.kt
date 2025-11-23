package nl.uwv.smz.diamond.view

import io.github.oshai.kotlinlogging.KotlinLogging.logger
import io.ktor.server.application.Application
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import nl.uwv.smz.diamond.logic_api.Service
import org.koin.ktor.ext.inject

private val log = logger {}

fun Application.installDiamondRouting() {
    log.info { "Installing routing..." }
    val service by inject<Service>()
    routing {
        get("/") {
            call.respondText(service.greet())
        }
    }
}
