package nl.uwv.smz.diamond.shared.common

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import kotlin.uuid.Uuid

fun interface UuidGenerator {
    fun generate(): Uuid
}

val RandomUuidGenerator = UuidGenerator { Uuid.random() }

@Suppress("TooGenericExceptionCaught")
fun Uuid.Companion.eitherParse(string: String): Either<IllegalArgumentException, Uuid> =
    try {
        Uuid.parse(string).right()
    } catch (e: IllegalArgumentException) {
        e.left()
    }
