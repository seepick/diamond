package nl.uwv.smz.diamond.view.model

import kotlinx.serialization.Serializable

@Serializable
data class HealthReportDto(
    val overallState: HealthStateDto,
    val overallTimeInMs: Int,
    val serviceDetails: List<HealthServiceDto>,
)

@Serializable
data class HealthServiceDto(
    val serviceName: String,
    val pingTimeInMs: Int,
    val state: HealthStateDto,
)

enum class HealthStateDto {
    HEALTHY,
    UNHEALTHY
}
