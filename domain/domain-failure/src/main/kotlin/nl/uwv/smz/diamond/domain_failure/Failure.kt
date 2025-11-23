package nl.uwv.smz.diamond.domain_failure

sealed interface Failure {
    val message: String

    data class NotFoundFailure(override val message: String) : Failure
    data class InvalidRequestFailure(override val message: String) : Failure
}
