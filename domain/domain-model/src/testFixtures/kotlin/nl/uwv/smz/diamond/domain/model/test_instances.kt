package nl.uwv.smz.diamond.domain.model

import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.property.Arb
import io.kotest.property.arbitrary.arbitrary
import io.kotest.property.arbitrary.int
import io.kotest.property.arbitrary.uuid
import kotlin.uuid.toKotlinUuid

fun PageRequest.Companion.default() =
    PageRequest(skip = 0, take = 10).shouldBeRight()

fun Arb.Companion.crystal() = arbitrary {
    Crystal(
        id = crystalId().bind(),
        weight = gram().bind(),
    )
}

fun Arb.Companion.crystalId() = arbitrary {
    CrystalId(uuid().bind().toKotlinUuid())
}

@Suppress("MagicNumber")
fun Arb.Companion.gram() = arbitrary {
    Gram(int(0..5000).bind()).shouldBeRight()
}

fun Arb.Companion.crystalCreate() = arbitrary {
    CrystalCreate(
        weight = gram().bind(),
    )
}

fun Arb.Companion.crystalUpdate() = arbitrary {
    CrystalUpdate(
        id = crystalId().bind(),
        weight = gram().bind(),
    )
}
