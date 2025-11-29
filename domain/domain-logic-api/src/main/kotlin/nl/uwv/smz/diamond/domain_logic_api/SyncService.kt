package nl.uwv.smz.diamond.domain_logic_api

import arrow.core.Either
import nl.uwv.smz.diamond.domainFailure.Failure

interface SyncService {
    suspend fun sync(): Either<Failure, Unit>
}
