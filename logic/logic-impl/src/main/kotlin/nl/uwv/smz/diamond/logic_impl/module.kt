package nl.uwv.smz.diamond.logic_impl

import nl.uwv.smz.diamond.logic_api.Service
import org.koin.dsl.module

fun logicImplModule() = module {
    single<Service> { ServiceImpl() }
//    singleOf(::UserRepositoryImpl) { bind<UserRepository>() }
    // singleOf(::UserService)
}
