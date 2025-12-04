@file:UseSerializers(LocalDateTimeSerializer::class)

package nl.uwv.smz.diamond.view.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import nl.uwv.smz.diamond.view.model.serializer.LocalDateTimeSerializer
import java.time.LocalDateTime

@Serializable
data class InfoDto(
    val version: String,
    @Serializable(with = LocalDateTimeSerializer::class)
    val buildTime: LocalDateTime,
)
