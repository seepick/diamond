package nl.uwv.smz.diamond.shared.test

import io.kotest.property.Arb
import io.kotest.property.arbitrary.arbitrary
import io.kotest.property.arbitrary.uuid
import kotlin.uuid.toKotlinUuid

fun Arb.Companion.kotlinUuid() = arbitrary {
    Arb.uuid().bind().toKotlinUuid()
}
