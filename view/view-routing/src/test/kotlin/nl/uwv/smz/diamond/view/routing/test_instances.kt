package nl.uwv.smz.diamond.view.routing

import io.kotest.property.Arb
import io.kotest.property.arbitrary.arbitrary
import io.kotest.property.arbitrary.int
import io.kotest.property.arbitrary.next
import io.kotest.property.arbitrary.string
import nl.uwv.smz.diamond.view.model.CrystalDto

fun Arb.Companion.crystalDto() = arbitrary {
    CrystalDto(
        id = string(1..20).next(),
        weightInGram = int(1..5000).next(),
    )
}
