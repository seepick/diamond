package nl.uwv.smz.diamond.view.routing

import io.github.oshai.kotlinlogging.KotlinLogging.logger
import io.ktor.server.application.Application
import io.ktor.server.request.receive
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import nl.uwv.smz.diamond.view.controller_api.CrystalController
import nl.uwv.smz.diamond.view.model.CrystalCreateDto
import org.koin.ktor.ext.inject

private val log = logger {}

internal fun Application.installCrystalRouting() {
    val controller by inject<CrystalController>()
    routing {
        // if (call.request.queryParameters["price"] == "asc") {
        get("/crystals") {
            log.info { "GET /crystals" }
            call.handle(controller.findAll())
        }
        get("/crystals/{id}") {
            val crystalId = call.parameters["id"]!! // that can NEVER be null!!?!??!?!!
            log.info { "GET /crystals/${crystalId}" }
            call.handle(controller.findSingle(crystalId))
        }
        post("/crystals") {
            val create = call.receive<CrystalCreateDto>()
            log.info { "POST /crystals" }
            call.handle(controller.create(create))
        }
        delete("/crystals/{id}") {
            val crystalId = call.parameters["id"]!!
            log.info { "DELETE /crystals" }
            call.handle(controller.delete(crystalId))
        }

    }
}
