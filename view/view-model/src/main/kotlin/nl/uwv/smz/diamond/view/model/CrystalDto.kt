package nl.uwv.smz.diamond.view.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import kotlin.uuid.Uuid

@Serializable
data class CrystalDto(
    val id: Uuid,
    val weightInGram: Int,
    val created: LocalDateTime,
)

@Serializable
data class CrystalCreateDto(val weightInGram: Int)

@Serializable
data class CrystalUpdateDto(val weightInGram: Int)
