package nl.uwv.smz.diamond.view.routing

import io.github.oshai.kotlinlogging.KotlinLogging.logger
import io.ktor.server.application.Application
import io.ktor.server.request.receive
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.routing
import nl.uwv.smz.diamond.view.controllerApi.CrystalController
import nl.uwv.smz.diamond.view.model.CrystalCreateDto
import nl.uwv.smz.diamond.view.model.CrystalUpdateDto
import org.koin.ktor.ext.inject

private val log = logger {}

internal fun Application.installCrystalRouting() {
    val controller by inject<CrystalController>()
    routing {
        get("/crystals") {
            log.info { "GET /crystals" }
            val pageRequest = call.request.readPageRequestDto()
            call.handle(controller.findAll(pageRequest))
        }
        get("/crystals/{id}") {
            val crystalId = call.parameters["id"]!!
            log.info { "GET /crystals/$crystalId" }
            call.handle(controller.findSingle(crystalId))
        }
        post("/crystals") {
            log.info { "POST /crystals" }
            val createDto = call.receive<CrystalCreateDto>()
            call.handle(controller.create(createDto))
        }
        put("/crystals/{id}") {
            val crystalId = call.parameters["id"]!!
            log.info { "POST /crystals/$crystalId" }
            val updateDto = call.receive<CrystalUpdateDto>()
            call.handle(controller.update(crystalId, updateDto))
        }
        delete("/crystals/{id}") {
            val crystalId = call.parameters["id"]!!
            log.info { "DELETE /crystals/$crystalId" }
            call.handle(controller.delete(crystalId))
        }
    }
}
