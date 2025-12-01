package nl.uwv.smz.diamond.view.controllerImpl

import nl.uwv.smz.diamond.shared.common.HealthableService
import nl.uwv.smz.diamond.shared.common.Modules
import nl.uwv.smz.diamond.view.controllerApi.CrystalController
import nl.uwv.smz.diamond.view.controllerApi.HealthController
import nl.uwv.smz.diamond.view.controllerApi.HomepageController
import nl.uwv.smz.diamond.view.controllerApi.InfoController
import nl.uwv.smz.diamond.view.controllerApi.PostsController
import nl.uwv.smz.diamond.view.controllerApi.SyncController
import org.koin.core.Koin
import org.koin.core.annotation.KoinInternalApi
import org.koin.core.definition.Kind
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import java.time.LocalDateTime
import kotlin.reflect.full.isSubclassOf

@Suppress("UnusedReceiverParameter")
fun Modules.controllerImpl(config: ControllerConfig) = module {
    singleOf(::HomepageControllerImpl) { bind<HomepageController>() }
    singleOf(::CrystalControllerImpl) { bind<CrystalController>() }
    singleOf(::PostsControllerImpl) { bind<PostsController>() }
    singleOf(::SyncControllerImpl) { bind<SyncController>() }
    single<HealthController> {
        HealthControllerImpl(getKoin().getAllSingletonsImplementing<HealthableService>())
    }
    single<InfoController> { InfoControllerImpl(config.appVersion, config.buildTime) }
}

data class ControllerConfig(
    val appVersion: String,
    val buildTime: LocalDateTime,
)

// TODO could move this to shared-koin
// this is a deficiency not yet resolved in koin: https://github.com/InsertKoinIO/koin/issues/2236
@OptIn(KoinInternalApi::class)
inline fun <reified T : Any> Koin.getAllSingletonsImplementing(): List<T> =
    instanceRegistry.instances
        .map { it.value.beanDefinition }
        .filter { it.kind == Kind.Singleton }
        .filter { it.primaryType.isSubclassOf(T::class) }
        .map { get<T>(clazz = it.primaryType, qualifier = null, parameters = null) }
        .distinct() // strangely duplicates in here :-/
