package nl.uwv.smz.diamond.domain.logicApi

import arrow.core.Either
import nl.uwv.smz.diamond.domain.failure.Failure

interface SyncService {
    suspend fun sync(): Either<Failure, Unit>
}
