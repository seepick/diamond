package nl.uwv.smz.diamond.view.routing

import io.github.oshai.kotlinlogging.KotlinLogging.logger
import io.ktor.server.application.Application
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import nl.uwv.smz.diamond.view.controllerApi.InfoController
import org.koin.ktor.ext.inject

private val log = logger {}

internal fun Application.installInfoRouting() {
    val controller by inject<InfoController>()
    routing {
        get("/info") {
            log.info { "GET /info" }
            call.respond(controller.info())
        }
    }
}
