package nl.uwv.smz.diamond.view.controllerImpl

import arrow.core.Either
import arrow.core.left
import arrow.core.raise.either
import arrow.core.right
import nl.uwv.smz.diamond.domain.failure.Failure
import nl.uwv.smz.diamond.domain.failure.mapLeftToBadRequest
import nl.uwv.smz.diamond.domain.model.Page
import nl.uwv.smz.diamond.domain.model.PageMeta
import nl.uwv.smz.diamond.domain.model.PageRequest
import nl.uwv.smz.diamond.view.controllerApi.QueryParamNames
import nl.uwv.smz.diamond.view.model.PageDto
import nl.uwv.smz.diamond.view.model.PageMetaDto
import nl.uwv.smz.diamond.view.model.PageRequestDto

fun PageRequestDto.toPageRequest(): Either<Failure.BadRequestFailure, PageRequest> = either {
    PageRequest(
        skip = skip.toIntOrDefault(QueryParamNames.Pagination.SKIP, PageRequest.DEFAULT_SKIP).bind(),
        take = take.toIntOrDefault(QueryParamNames.Pagination.TAKE, PageRequest.DEFAULT_TAKE).bind(),
    ).mapLeftToBadRequest().bind()
}

private fun String?.toIntOrDefault(paramName: String, defaultValue: Int) = either {
    if (this@toIntOrDefault == null) {
        defaultValue.right().bind()
    } else {
        this@toIntOrDefault.toIntOrNull()?.right()?.bind()
            ?: Failure.BadRequestFailure(
                "Param '$paramName' must be a valid number but was: '${this@toIntOrDefault}'",
            ).left().bind()
    }
}

fun <IN, OUT> Page<IN>.toDto(mapper: (IN) -> OUT) = PageDto(
    meta = meta.toDto(),
    items = items.map(mapper),
)

fun PageMeta.toDto() = PageMetaDto(
    skip = skip,
    take = take,
    hasMore = hasMore,
)
