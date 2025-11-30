package nl.uwv.smz.diamond.view.controllerImpl

import arrow.core.raise.either
import nl.uwv.smz.diamond.domain.logicApi.SyncService
import nl.uwv.smz.diamond.view.controllerApi.SyncController

class SyncControllerImpl(private val syncService: SyncService) : SyncController {
    override suspend fun sync() = either {
        syncService.sync().bind()
    }
}
