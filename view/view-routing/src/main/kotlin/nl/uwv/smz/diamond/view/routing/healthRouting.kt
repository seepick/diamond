package nl.uwv.smz.diamond.view.routing

import io.ktor.server.application.Application
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import nl.uwv.smz.diamond.view.controllerApi.HealthController
import org.koin.ktor.ext.inject

internal fun Application.installHealthRouting() {
    val controller by inject<HealthController>()
    routing {
        get("/health") {
            call.respond(controller.fetchHealthReport())
        }
    }
}
