package nl.uwv.smz.diamond.domain.failure

import arrow.core.Either

sealed interface Failure {
    val code: ErrorCode
    val message: String
    val exception: Exception?

    data class NotFoundFailure(override val message: String, override val exception: Exception? = null) : Failure {
        override val code = ErrorCode.NOT_FOUND
    }

    data class CorruptDataFailure(override val message: String, override val exception: Exception? = null) : Failure {
        override val code = ErrorCode.CORRUPT_DATA
    }

    data class ConnectionError(override val message: String, override val exception: Exception? = null) : Failure {
        override val code = ErrorCode.INTERNAL_ERROR
    }

    data class BadRequestFailure(override val message: String, override val exception: Exception? = null) : Failure {
        override val code = ErrorCode.BAD_CLIENT_REQUEST
    }
}

fun <X> Either<Failure.CorruptDataFailure, X>.mapLeftToBadRequest(): Either<Failure.BadRequestFailure, X> =
    mapLeft { Failure.BadRequestFailure(it.message) }
