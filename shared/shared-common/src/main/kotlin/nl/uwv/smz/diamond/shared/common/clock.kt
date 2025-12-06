package nl.uwv.smz.diamond.shared.common

import java.time.LocalDateTime

fun interface Clock {
    fun now(): LocalDateTime
}

val SystemClock = Clock { LocalDateTime.now() }
