package nl.uwv.smz.diamond.view.controller_impl

import nl.uwv.smz.diamond.shared.common.Modules
import nl.uwv.smz.diamond.view.controller_api.HomepageController
import org.koin.dsl.module

fun Modules.controllerImpl() = module {
    single<HomepageController> { HomepageControllerImpl(get()) }
}
