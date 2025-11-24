package nl.uwv.smz.diamond.view.controller_impl

import nl.uwv.smz.diamond.shared.common.Modules
import nl.uwv.smz.diamond.view.controller_api.CrystalController
import nl.uwv.smz.diamond.view.controller_api.HomepageController
import org.koin.dsl.module

@Suppress("UnusedReceiverParameter")
fun Modules.controllerImpl() = module {
    single<HomepageController> { HomepageControllerImpl(get()) }
    single<CrystalController> { CrystalControllerImpl(get()) }
}
