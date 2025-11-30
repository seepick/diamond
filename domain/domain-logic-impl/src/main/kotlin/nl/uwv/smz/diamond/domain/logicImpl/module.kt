package nl.uwv.smz.diamond.domain.logicImpl

import nl.uwv.smz.diamond.domain.logicApi.CrystalService
import nl.uwv.smz.diamond.domain.logicApi.GreetService
import nl.uwv.smz.diamond.domain.logicApi.PostsService
import nl.uwv.smz.diamond.domain.logicApi.SyncService
import nl.uwv.smz.diamond.shared.common.Modules
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

@Suppress("UnusedReceiverParameter")
fun Modules.domainLogicImpl() = module {
    singleOf(::GreetServiceImpl) { bind<GreetService>() }
    singleOf(::CrystalServiceImpl) { bind<CrystalService>() }
    singleOf(::PostsServiceImpl) { bind<PostsService>() }
    singleOf(::SyncServiceImpl) { bind<SyncService>() }
}
