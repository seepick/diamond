package nl.uwv.smz.diamond.view.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiErrorDto(
    val code: String,
    val message: String,
)
