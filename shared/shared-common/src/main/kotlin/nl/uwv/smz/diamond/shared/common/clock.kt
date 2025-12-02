package nl.uwv.smz.diamond.shared.common

import java.time.LocalDateTime
import kotlin.time.ExperimentalTime

interface Clock {
    fun now(): LocalDateTime
}

@OptIn(ExperimentalTime::class)
object SystemClock : Clock {
    override fun now(): LocalDateTime = LocalDateTime.now()
}
