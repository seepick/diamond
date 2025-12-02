package nl.uwv.smz.diamond.view.model

import io.kotest.property.Arb
import io.kotest.property.arbitrary.arbitrary
import io.kotest.property.arbitrary.int
import io.kotest.property.arbitrary.localDateTime
import nl.uwv.smz.diamond.shared.test.kotlinUuid

@Suppress("MagicNumber")
fun Arb.Companion.crystalDto() = arbitrary {
    CrystalDto(
        id = kotlinUuid().bind(),
        created = localDateTime().bind(),
        weightInGram = int(1..5000).bind(),
    )
}
