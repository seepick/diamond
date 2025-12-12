package nl.uwv.smz.diamond.app

import io.github.oshai.kotlinlogging.KotlinLogging.logger
import io.ktor.server.application.Application
import io.ktor.server.application.install
import nl.uwv.smz.diamond.domain.logicImpl.domainLogicImpl
import nl.uwv.smz.diamond.extern.impl.externImpl
import nl.uwv.smz.diamond.persistence.impl.persistenceImpl
import nl.uwv.smz.diamond.shared.common.Clock
import nl.uwv.smz.diamond.shared.common.Modules
import nl.uwv.smz.diamond.shared.common.RandomUuidGenerator
import nl.uwv.smz.diamond.shared.common.SystemClock
import nl.uwv.smz.diamond.shared.common.UuidGenerator
import nl.uwv.smz.diamond.view.controllerImpl.ControllerConfig
import nl.uwv.smz.diamond.view.controllerImpl.controllerImpl
import org.koin.core.logger.Level
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.ktor.plugin.KoinIsolated
import org.koin.logger.slf4jLogger

private val log = logger {}

fun Application.installKoin(
    config: GlobalConfiguration,
    externStub: Module?,
) {
    log.info { "Installing Koin" }
    install(KoinIsolated) {
        // don't use the regular `Koin` one (for parallel tests)
        // imported to install the isolated version of it!
        slf4jLogger(level = Level.DEBUG) // TODO extract as EnvConfig, default = WARN
        allowOverride(false)
        modules(Modules.all(config, externStub))
    }
}

fun Modules.all(
    config: GlobalConfiguration,
    externStub: Module? = null, // FIXME NO! do a module override!
) = listOf(
    sharedModule(),
    controllerImpl(
        ControllerConfig(
            appVersion = config.build.appVersion,
            buildTime = config.build.buildTime,
        ),
    ),
    domainLogicImpl(),
    persistenceImpl(config.env.database),
    externStub ?: externImpl(config.env.extern),
)

// generic utils from shared-common
@Suppress("UnusedReceiverParameter")
fun Modules.sharedModule() = module {
    single<Clock> { SystemClock }
    single<UuidGenerator> { RandomUuidGenerator }
}
