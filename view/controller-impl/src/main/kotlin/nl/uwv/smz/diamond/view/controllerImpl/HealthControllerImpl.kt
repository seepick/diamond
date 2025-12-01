package nl.uwv.smz.diamond.view.controllerImpl

import nl.uwv.smz.diamond.shared.common.HealthState
import nl.uwv.smz.diamond.shared.common.HealthableService
import nl.uwv.smz.diamond.shared.common.ServiceHealthInfo
import nl.uwv.smz.diamond.view.controllerApi.HealthController
import nl.uwv.smz.diamond.view.model.HealthReportDto
import nl.uwv.smz.diamond.view.model.HealthServiceDto
import nl.uwv.smz.diamond.view.model.HealthStateDto

class HealthControllerImpl(
    private val healthableServices: List<HealthableService>
) : HealthController {
    init {
        println("XXXXX=${healthableServices.size}")
    }

    override fun fetchHealthReport() = healthableServices.map { it.healthInfo() }.let { infos ->
        HealthReportDto(
            overallState = infos.all { it.state == HealthState.Healthy }.toHealthStateDto(),
            overallTimeInMs = infos.sumOf { it.pingTimeInMs },
            serviceDetails = infos.map { it.toDto() },
        )
    }
}

private fun Boolean.toHealthStateDto() = when (this) {
    true -> HealthStateDto.HEALTHY
    else -> HealthStateDto.UNHEALTHY
}

private fun HealthState.toDto() = when (this) {
    HealthState.Healthy -> HealthStateDto.HEALTHY
    HealthState.Unhealthy -> HealthStateDto.UNHEALTHY
}

private fun ServiceHealthInfo.toDto() = HealthServiceDto(
    serviceName = serviceName,
    pingTimeInMs = pingTimeInMs,
    state = state.toDto(),
)
