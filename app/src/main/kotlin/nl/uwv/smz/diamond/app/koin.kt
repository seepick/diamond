package nl.uwv.smz.diamond.app

import io.github.oshai.kotlinlogging.KotlinLogging.logger
import io.ktor.server.application.Application
import io.ktor.server.application.install
import nl.uwv.smz.diamond.domain_logic_impl.domainLogicImpl
import nl.uwv.smz.diamond.persistence.impl.persistenceImpl
import nl.uwv.smz.diamond.persistence.stub.persistenceStub
import nl.uwv.smz.diamond.shared.common.Modules
import nl.uwv.smz.diamond.view.controller_impl.ControllerConfig
import nl.uwv.smz.diamond.view.controller_impl.controllerImpl
import org.koin.core.logger.Level
import org.koin.ktor.plugin.KoinIsolated
import org.koin.logger.slf4jLogger

private val log = logger {}

fun Application.installKoin(config: GlobalConfig) {
    log.info { "Installing Koin" }
    install(KoinIsolated) {
        slf4jLogger(level = Level.INFO) // TODO extract as EnvConfig, default = WARN
        modules(Modules.all(config))
    }
}

fun Modules.all(config: GlobalConfig) = listOf(
    controllerImpl(
        ControllerConfig(
            appVersion = config.build.appVersion,
            buildTime = config.build.buildTime,
        )
    ),
    domainLogicImpl(),
    when (config.env.database.stubEnabled) {
        true -> persistenceStub()
        false -> persistenceImpl(config.env.database)
    },
)
