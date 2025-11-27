package nl.uwv.smz.diamond.view.model

import kotlinx.serialization.Serializable

@Serializable
data class CrystalDto(
    val id: String,
    val weightInGram: Int,
)

@Serializable
data class CrystalCreateDto(val weightInGram: Int)

@Serializable
data class CrystalUpdateDto(val weightInGram: Int)
