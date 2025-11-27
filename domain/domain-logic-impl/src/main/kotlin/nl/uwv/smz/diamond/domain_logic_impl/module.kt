package nl.uwv.smz.diamond.domain_logic_impl

import nl.uwv.smz.diamond.domain_logic_api.CrystalService
import nl.uwv.smz.diamond.domain_logic_api.GreetService
import nl.uwv.smz.diamond.domain_logic_api.PostsService
import nl.uwv.smz.diamond.shared.common.Modules
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

@Suppress("UnusedReceiverParameter")
fun Modules.domainLogicImpl() = module {
    single<GreetService> { GreetServiceImpl() }
    single<CrystalService> { CrystalServiceImpl(get()) }
//    single<PostsService> { PostsServiceImpl(get()) }
    singleOf(::PostsServiceImpl) { bind<PostsService>() }
}
