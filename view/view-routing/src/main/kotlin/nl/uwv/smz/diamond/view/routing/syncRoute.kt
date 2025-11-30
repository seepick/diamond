package nl.uwv.smz.diamond.view.routing

import io.github.oshai.kotlinlogging.KotlinLogging.logger
import io.ktor.server.application.Application
import io.ktor.server.routing.put
import io.ktor.server.routing.routing
import nl.uwv.smz.diamond.view.controllerApi.SyncController
import org.koin.ktor.ext.inject

private val log = logger {}

internal fun Application.installSyncRouting() {
    val controller by inject<SyncController>()
    routing {
        put("/sync") {
            log.info { "PUT /sync" }
            call.handle(controller.sync())
        }
    }
}
