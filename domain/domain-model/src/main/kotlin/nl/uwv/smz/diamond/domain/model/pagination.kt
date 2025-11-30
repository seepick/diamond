package nl.uwv.smz.diamond.domain.model

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import nl.uwv.smz.diamond.domain.failure.Failure

data class Page<T>(
    val meta: PageMeta,
    val items: List<T>,
) : Iterable<T> by items {
    companion object // for extensions
}

data class PageMeta(
    /** From request. */
    val skip: Int,
    /** From request. */
    val take: Int,
    val hasMore: Boolean,
)

@ConsistentCopyVisibility // kotlin will soon (v2.23?) support copy with same visibility as constructor
data class PageRequest private constructor(
    val skip: Int = DEFAULT_SKIP,
    val take: Int = DEFAULT_TAKE,
) {
    companion object {
        const val DEFAULT_SKIP = 0
        const val DEFAULT_TAKE = 10
        const val MIN_SKIP = 0
        const val MIN_TAKE = 1
        const val SKIP_NAME = "skip"
        const val TAKE_NAME = "take"

        operator fun invoke(skip: Int, take: Int): Either<Failure.CorruptDataFailure, PageRequest> = either {
            PageRequest(
                skip = skip.ensureMin(SKIP_NAME, MIN_SKIP).bind(),
                take = take.ensureMin(TAKE_NAME, MIN_TAKE).bind(),
            )
        }

        private fun Int.ensureMin(paramName: String, minValue: Int) = either {
            ensure(this@ensureMin >= minValue) {
                Failure.CorruptDataFailure("Param '$paramName' must be >= $minValue but was: ${this@ensureMin}")
            }
            this@ensureMin
        }
    }
}
