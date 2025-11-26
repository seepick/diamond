package nl.uwv.smz.diamond.domain.model

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import nl.uwv.smz.diamond.domainFailure.Failure
import nl.uwv.smz.diamond.shared.common.eitherParse
import kotlin.uuid.Uuid

@JvmInline
value class Gram private constructor(val value: Int) {
    companion object {
        operator fun invoke(value: Int) = either {
            ensure(value >= 0) {
                Failure.CorruptDataFailure("Gram must not be negative: $value")
            }
            Gram(value)
        }
    }

    operator fun plus(addition: Int) = Gram(value + addition)
}

@JvmInline
value class CrystalId(val value: Uuid) {
    companion object {
        // make sure BadDataFailure is mapped differently for incoming request (400 Bad Request)
        // and internal outgoing data like corrupt DB entries (500 Internal Error)
        operator fun invoke(value: String): Either<Failure.CorruptDataFailure, CrystalId> = either {
            CrystalId(Uuid.eitherParse(value).mapLeft { Failure.CorruptDataFailure(it.message ?: "") }.bind())
        }

        fun random() = CrystalId(Uuid.random())
    }

    override fun toString() = value.toString()
}

data class Crystal(
    val id: CrystalId,
    val weight: Gram,
) {
    companion object // for extensions
}

data class CrystalCreate(
    val weight: Gram,
)

data class CrystalUpdate(
    val id: CrystalId,
    val weight: Gram,
)
