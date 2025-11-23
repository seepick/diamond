package nl.uwv.smz.diamond.view.routing

import io.github.oshai.kotlinlogging.KotlinLogging.logger
import io.ktor.server.application.Application
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import nl.uwv.smz.diamond.view.controller_api.CrystalController
import org.koin.ktor.ext.inject

private val log = logger {}

internal fun Application.installCrystalRouting() {
    val controller by inject<CrystalController>()
    routing {
        get("/crystals") {
            log.info { "GET /crystals" }
            call.respond(controller.findAll())
        }
        get("/crystals/{id}") {
            val crystalId = call.parameters["id"]!! // TODO that can NEVER be null!!?!??!?!!
            log.info { "GET /crystals/${crystalId}" }
            call.handle(controller.findSingle(crystalId))
        }
    }
}
