package nl.uwv.smz.diamond.view.controller_impl

import arrow.core.raise.either
import nl.uwv.smz.diamond.domain_logic_api.SyncService
import nl.uwv.smz.diamond.view.controller_api.SyncController

class SyncControllerImpl(private val syncService: SyncService) : SyncController {
    override suspend fun sync() = either {
        syncService.sync().bind()
    }
}
