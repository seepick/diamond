package nl.uwv.smz.diamond.view.routing

import io.github.oshai.kotlinlogging.KotlinLogging.logger
import io.ktor.server.application.Application
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import nl.uwv.smz.diamond.view.controllerApi.HomepageController
import org.koin.ktor.ext.inject

private val log = logger {}

internal fun Application.installHomepageRouting() {
    val controller by inject<HomepageController>()
    routing {
        get("/") {
            log.info { "GET /" }
            call.respondText(controller.greet())
        }
    }
}
