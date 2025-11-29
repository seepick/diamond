package nl.uwv.smz.diamond.view.controller_api

import arrow.core.Either
import nl.uwv.smz.diamond.domainFailure.Failure

interface SyncController {
    suspend fun sync(): Either<Failure, Unit>
}
