package nl.uwv.smz.diamond.persistence.impl

import nl.uwv.smz.diamond.domain.model.Page
import nl.uwv.smz.diamond.domain.model.PageMeta
import nl.uwv.smz.diamond.domain.model.PageRequest

fun <T> List<T>.toPage(pageRequest: PageRequest, hasMore: Boolean) = Page(
    meta = PageMeta(
        skip = pageRequest.skip,
        take = pageRequest.take,
        hasMore = hasMore,
    ),
    items = this,
)
