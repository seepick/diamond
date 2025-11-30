package nl.uwv.smz.diamond.view.routing

import io.kotest.property.Arb
import io.kotest.property.arbitrary.arbitrary
import io.kotest.property.arbitrary.int
import nl.uwv.smz.diamond.view.model.CrystalDto

fun Arb.Companion.crystalDto() = arbitrary {
    CrystalDto(
        id = kotlinUuid().bind(),
        weightInGram = int(1..5000).bind(),
    )
}
