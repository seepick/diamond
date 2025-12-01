package nl.uwv.smz.diamond.domain.model

import arrow.core.Either
import arrow.core.left
import arrow.core.raise.either
import arrow.core.right
import nl.uwv.smz.diamond.domain.failure.Failure

interface SortingsRequest<REQUEST : SortRequest<FIELD>, FIELD : SortField> {
    val sorts: List<REQUEST>
}

interface SortRequest<FIELD : SortField> {
    val direction: SortDirection
    val field: FIELD
}

enum class SortDirection {
    Asc,
    Desc;

    companion object {
        val Default = Asc
    }
}

interface SortField {
    val queryName: String
}

abstract class SortFieldFinder<FIELD_TYPE : SortField>(sorts: List<FIELD_TYPE>) {
    private val sortsByQueryName by lazy {
        sorts.associateBy { it.queryName }
    }

    private val availableQueryNames by lazy {
        sorts.joinToString(", ") { it.queryName }
    }

    operator fun get(queryName: String): Either<Failure.BadRequestFailure, FIELD_TYPE> = either {
        val sort = sortsByQueryName[queryName] ?: Failure.BadRequestFailure(
            "Sort field not found: $queryName! Available: $availableQueryNames",
        ).left().bind()
        sort.right().bind()
    }
}
