@file:UseSerializers(LocalDateTimeSerializer::class)

package nl.uwv.smz.diamond.view.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.LocalDateTime

@Serializable
data class InfoDto(
    val version: String,
    val buildTime: LocalDateTime,
)
