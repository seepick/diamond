package nl.uwv.smz.diamond.shared.test

import io.kotest.property.Arb
import io.kotest.property.arbitrary.arbitrary
import io.kotest.property.arbitrary.localDateTime
import kotlinx.datetime.toKotlinLocalDateTime

fun Arb.Companion.kotlinLocalDateTime() = arbitrary {
    localDateTime().bind().toKotlinLocalDateTime()
}
