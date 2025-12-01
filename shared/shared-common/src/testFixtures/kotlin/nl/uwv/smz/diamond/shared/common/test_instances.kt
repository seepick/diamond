package nl.uwv.smz.diamond.shared.common

import kotlinx.datetime.LocalDateTime
import kotlin.uuid.Uuid

class StaticClock(private val now: LocalDateTime) : Clock {
    override fun now() = now
}

class StaticUuidGenerator(private val uuid: Uuid) : UuidGenerator {
    override fun generate() = uuid
}
