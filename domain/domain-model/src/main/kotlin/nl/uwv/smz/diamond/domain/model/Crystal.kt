package nl.uwv.smz.diamond.domain.model

import arrow.core.Either
import arrow.core.raise.either
import nl.uwv.smz.diamond.domain_failure.Failure
import nl.uwv.smz.diamond.shared.common.eitherParse
import kotlin.uuid.Uuid

@JvmInline
value class Gram(val value: Int)

@JvmInline
value class CrystalId private constructor(val value: Uuid) {
    companion object {
        // make sure BadDataFailure is mapped differently for incoming request (400 Bad Request) and internal outgoing data like corrupt DB entries (500 Internal Error)
        operator fun invoke(value: String): Either<Failure.BadDataFailure, CrystalId> = either {
            CrystalId(Uuid.eitherParse(value).mapLeft { Failure.BadDataFailure(it.message ?: "") }.bind())
        }
    }
}

data class Crystal(
    val id: CrystalId,
    val weight: Gram,
)

data class CrystalCreate(
    val weight: Gram,
)
