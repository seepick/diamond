package nl.uwv.smz.diamond.itest

import nl.uwv.smz.diamond.view.model.PageDto
import nl.uwv.smz.diamond.view.model.PageMetaDto

fun <T> PageDto.Companion.emptyDefault() =
    // TODO skip and take needs adjustment based on previous request
    PageDto<T>(PageMetaDto(0, 10, false), emptyList())
