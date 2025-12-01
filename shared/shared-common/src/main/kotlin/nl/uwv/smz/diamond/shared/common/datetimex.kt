package nl.uwv.smz.diamond.shared.common

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class) // TODO should be disabled by gradle build?!?
fun LocalDateTime.Companion.now() =
    Clock.System.now().toLocalDateTime(TimeZone.UTC)
