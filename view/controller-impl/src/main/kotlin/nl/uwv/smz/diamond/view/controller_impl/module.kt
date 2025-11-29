package nl.uwv.smz.diamond.view.controller_impl

import nl.uwv.smz.diamond.shared.common.Modules
import nl.uwv.smz.diamond.view.controller_api.CrystalController
import nl.uwv.smz.diamond.view.controller_api.HomepageController
import nl.uwv.smz.diamond.view.controller_api.InfoController
import nl.uwv.smz.diamond.view.controller_api.PostsController
import nl.uwv.smz.diamond.view.controller_api.SyncController
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import java.time.LocalDateTime

@Suppress("UnusedReceiverParameter")
fun Modules.controllerImpl(config: ControllerConfig) = module {
    singleOf(::HomepageControllerImpl) { bind<HomepageController>() }
    singleOf(::CrystalControllerImpl) { bind<CrystalController>() }
    singleOf(::PostsControllerImpl) { bind<PostsController>() }
    singleOf(::SyncControllerImpl) { bind<SyncController>() }
    single<InfoController> { InfoControllerImpl(config.appVersion, config.buildTime) }
}

data class ControllerConfig(
    val appVersion: String,
    val buildTime: LocalDateTime,
)
