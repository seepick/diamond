package nl.uwv.smz.diamond.view.controller_impl

import nl.uwv.smz.diamond.shared.common.Modules
import nl.uwv.smz.diamond.view.controller_api.CrystalController
import nl.uwv.smz.diamond.view.controller_api.HomepageController
import nl.uwv.smz.diamond.view.controller_api.InfoController
import nl.uwv.smz.diamond.view.controller_api.PostsController
import org.koin.dsl.module
import java.time.LocalDateTime

@Suppress("UnusedReceiverParameter")
fun Modules.controllerImpl(config: ControllerConfig) = module {
    single<HomepageController> { HomepageControllerImpl(get()) }
    single<CrystalController> { CrystalControllerImpl(get()) }
    single<PostsController> { PostsControllerImpl(get()) }
    single<InfoController> { InfoControllerImpl(config.appVersion, config.buildTime) }
}

data class ControllerConfig(
    val appVersion: String,
    val buildTime: LocalDateTime,
)
