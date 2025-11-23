package nl.uwv.smz.diamond.domain.model

import kotlin.uuid.Uuid

@JvmInline
value class Gram(val value: Int)

@JvmInline
value class CrystalId(val value: Uuid)

data class Crystal(
    val id: CrystalId,
    val weight: Gram,
)

data class CrystalCreate(
    val weight: Gram,
)
