package nl.uwv.smz.diamond.view.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiErrorDto(
    // TODO use type ErrorCode but use its `renderedValue` to render JSON
    val code: String,
    val message: String,
)
