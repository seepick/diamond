package nl.uwv.smz.diamond.view.model

import kotlinx.serialization.Serializable

/** Passed as two query parameters. */
data class PageRequestDto(
    /** aka offset */
    val skip: String?,
    /** aka limit */
    val take: String?,
) {
    companion object // for extensions
}

@Serializable
data class PageDto<ITEM>(
    val meta: PageMetaDto,
    val items: List<ITEM>,
) {
    companion object // for extensions
}

@Serializable
data class PageMetaDto(
    val skip: Int,
    val take: Int,
    val hasMore: Boolean,
    // total?
    // navigation urls/headers...
)
