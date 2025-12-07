package nl.uwv.smz.diamond.domain.failure

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
            requireNotNull(byRenderedValue[value]) { "Unknown error code value [$value]" }
    }
}
