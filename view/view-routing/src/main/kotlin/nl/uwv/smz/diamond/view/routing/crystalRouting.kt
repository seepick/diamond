package nl.uwv.smz.diamond.view.routing

import io.github.oshai.kotlinlogging.KotlinLogging.logger
import io.ktor.server.application.Application
import io.ktor.server.request.receive
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import nl.uwv.smz.diamond.view.controllerApi.CrystalController
import nl.uwv.smz.diamond.view.controllerApi.QueryParamNames
import nl.uwv.smz.diamond.view.model.CrystalCreateDto
import nl.uwv.smz.diamond.view.model.CrystalUpdateDto
import org.koin.ktor.ext.inject

private val log = logger {}

internal fun Application.installCrystalRouting() {
    val controller by inject<CrystalController>()
    routing {
        route("/crystals") {
            get("") {
                log.info { "GET /crystals" }
                val pageRequest = call.request.readPageRequestDto()
                val sort = call.request.queryParameters[QueryParamNames.SORT]
                call.handle(controller.findAll(pageRequest, sort))
            }
            get("/{id}") {
                val crystalId = call.parameters["id"]!!
                log.info { "GET /crystals/$crystalId" }
                call.handle(controller.findSingle(crystalId))
            }
            post("") {
                log.info { "POST /crystals" }
                val createDto = call.receive<CrystalCreateDto>()
                call.handle(controller.create(createDto))
            }
            put("/{id}") {
                val crystalId = call.parameters["id"]!!
                log.info { "POST /crystals/$crystalId" }
                val updateDto = call.receive<CrystalUpdateDto>()
                call.handle(controller.update(crystalId, updateDto))
            }
            delete("/{id}") {
                val crystalId = call.parameters["id"]!!
                log.info { "DELETE /crystals/$crystalId" }
                call.handle(controller.delete(crystalId))
            }
        }
    }
}
