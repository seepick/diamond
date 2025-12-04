package nl.uwv.smz.diamond.view.model

import kotlinx.serialization.Serializable
import nl.uwv.smz.diamond.view.model.serializer.LocalDateTimeSerializer
import java.time.LocalDateTime
import kotlin.uuid.Uuid

@Serializable
data class CrystalDto(
    val id: Uuid,
    val weightInGram: Int,
    @Serializable(with = LocalDateTimeSerializer::class)
    val created: LocalDateTime,
)

@Serializable
data class CrystalCreateDto(val weightInGram: Int)

@Serializable
data class CrystalUpdateDto(val weightInGram: Int)
