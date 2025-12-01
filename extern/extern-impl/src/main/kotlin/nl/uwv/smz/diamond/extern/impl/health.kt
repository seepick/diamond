package nl.uwv.smz.diamond.extern.impl

import io.github.oshai.kotlinlogging.KotlinLogging
import nl.uwv.smz.diamond.shared.common.HealthState
import kotlin.system.measureTimeMillis

private val log = KotlinLogging.logger {}

@Suppress("TooGenericExceptionCaught")
internal fun safeMeasureSuccessful(throwingCode: () -> Unit): Pair<Int, HealthState> {
    var successful = false
    val timeNeeded = measureTimeMillis {
        try {
            throwingCode()
            successful = true
        } catch (e: Exception) {
            log.debug(e) { "Ignoring exception for health check ping only." }
        }
    }
    return timeNeeded.toInt() to successful.toHealthState()
}

internal fun Boolean.toHealthState() = when (this) {
    true -> HealthState.Healthy
    else -> HealthState.Unhealthy
}
