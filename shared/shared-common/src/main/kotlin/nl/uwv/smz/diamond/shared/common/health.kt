package nl.uwv.smz.diamond.shared.common

interface HealthableService {
    fun healthInfo(): ServiceHealthInfo
}

data class ServiceHealthInfo(
    val serviceName: String,
    val pingTimeInMs: Int,
    val state: HealthState,
)

enum class HealthState {
    Healthy,
    Unhealthy
}
