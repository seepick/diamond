package nl.uwv.smz.diamond.domain_failure

sealed interface Failure {
    val code: String
    val message: String

    data class NotFoundFailure(override val message: String) : Failure {
        override val code = "NOT_FOUND"
    }

    data class BadDataFailure(override val message: String) : Failure {
        override val code = "BAD_DATA"
    }

    data class InvalidRequestFailure(override val message: String) : Failure {
        override val code = "INVALID_REQUEST"
    }
}
