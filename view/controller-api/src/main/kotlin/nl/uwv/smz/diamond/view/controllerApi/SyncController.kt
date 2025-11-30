package nl.uwv.smz.diamond.view.controllerApi

import arrow.core.Either
import nl.uwv.smz.diamond.domain.failure.Failure

interface SyncController {
    suspend fun sync(): Either<Failure, Unit>
}
