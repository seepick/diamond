package nl.uwv.smz.diamond.domain_failure

import arrow.core.Either

sealed interface Failure {
    val code: String
    val message: String

    data class NotFoundFailure(override val message: String) : Failure {
        override val code = "NOT_FOUND"
    }

    data class CorruptDataFailure(override val message: String) : Failure {
        override val code = "CORRUPT_DATA"
    }

    data class BadRequestFailure(override val message: String) : Failure {
        override val code = "BAD_REQUEST"
    }
}

fun <X> Either<Failure.CorruptDataFailure, X>.mapToBadRequest(): Either<Failure.BadRequestFailure, X> =
    mapLeft { Failure.BadRequestFailure(it.message) }
