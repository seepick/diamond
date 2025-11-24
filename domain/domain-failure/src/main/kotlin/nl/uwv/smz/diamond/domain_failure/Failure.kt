package nl.uwv.smz.diamond.domain_failure

import arrow.core.Either

enum class ErrorCode(val renderedValue: String) {
    NOT_FOUND("NOT_FOUND"),
    CORRUPT_DATA("CORRUPT_DATA"),
    BAD_REQUEST("BAD_REQUEST"),
    INTERNAL_ERROR("INTERNAL_ERROR"),
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
        override val code = ErrorCode.BAD_REQUEST
    }
}

fun <X> Either<Failure.CorruptDataFailure, X>.mapToBadRequest(): Either<Failure.BadRequestFailure, X> =
    mapLeft { Failure.BadRequestFailure(it.message) }
