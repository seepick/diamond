package nl.uwv.smz.diamond.domain_logic_impl

import nl.uwv.smz.diamond.domain_logic_api.GreetService
import nl.uwv.smz.diamond.shared.common.Modules
import org.koin.dsl.module

fun Modules.domainLogicImpl() = module {
    single<GreetService> { GreetServiceImpl() }
//    singleOf(::UserRepositoryImpl) { bind<UserRepository>() }
    // singleOf(::UserService)
}
