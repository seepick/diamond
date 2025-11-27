package nl.uwv.smz.diamond.view.model

import kotlinx.serialization.Serializable

@Serializable
data class PostDto(
    val id: Int,
    val title: String,
)
