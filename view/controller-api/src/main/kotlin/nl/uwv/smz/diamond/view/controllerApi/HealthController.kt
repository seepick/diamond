package nl.uwv.smz.diamond.view.controllerApi

import nl.uwv.smz.diamond.view.model.HealthReportDto

interface HealthController {
    fun fetchHealthReport(): HealthReportDto
}
