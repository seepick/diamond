package nl.uwv.smz.diamond.shared.common

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime

interface Clock {
    fun now(): LocalDateTime
}

@OptIn(ExperimentalTime::class)
object SystemClock : Clock {
    override fun now(): LocalDateTime =
        kotlin.time.Clock.System.now().toLocalDateTime(TimeZone.UTC)
}
