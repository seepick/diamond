package nl.uwv.smz.diamond.view.controllerImpl

import nl.uwv.smz.diamond.view.controllerApi.InfoController
import nl.uwv.smz.diamond.view.model.InfoDto
import java.time.LocalDateTime

// no need to delegate down to a domain service
class InfoControllerImpl(
    private val appVersion: String,
    private val buildTime: LocalDateTime
) : InfoController {
    override fun info() =
        InfoDto(
            version = appVersion,
            buildTime = buildTime,
        )
}
