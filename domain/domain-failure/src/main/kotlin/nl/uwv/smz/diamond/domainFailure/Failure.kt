package nl.uwv.smz.diamond.domainFailure

import arrow.core.Either

enum class ErrorCode(val renderedValue: String) {
    NOT_FOUND("NOT_FOUND"),
    CORRUPT_DATA("CORRUPT_DATA"),
    // it's safe to rename (refactor) these
    // separate A) internal identifiers for developers, and B) public facing API values
    BAD_CLIENT_REQUEST("BAD_REQUEST"),
    INTERNAL_ERROR("INTERNAL_ERROR"),
    ;

    companion object {
        private val byRenderedValue by lazy {
            entries.associateBy { it.renderedValue }
        }

        fun byRenderedValueOrThrow(value: String): ErrorCode =
            byRenderedValue[value] ?: throw IllegalArgumentException("Unknown error code value [$value]")
    }
}

sealed interface Failure {
    val code: ErrorCode
    val message: String

    data class NotFoundFailure(override val message: String) : Failure {
        override val code = ErrorCode.NOT_FOUND
    }

    data class CorruptDataFailure(override val message: String) : Failure {
        override val code = ErrorCode.CORRUPT_DATA
    }

    data class BadRequestFailure(override val message: String) : Failure {
        override val code = ErrorCode.BAD_CLIENT_REQUEST
    }
}

fun <X> Either<Failure.CorruptDataFailure, X>.mapToBadRequest(): Either<Failure.BadRequestFailure, X> =
    mapLeft { Failure.BadRequestFailure(it.message) }
