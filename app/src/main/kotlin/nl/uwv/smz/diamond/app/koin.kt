package nl.uwv.smz.diamond.app

import io.github.oshai.kotlinlogging.KotlinLogging.logger
import io.ktor.server.application.Application
import io.ktor.server.application.install
import nl.uwv.smz.diamond.domain_logic_impl.domainLogicImpl
import nl.uwv.smz.diamond.persistence.impl.persistenceImpl
import nl.uwv.smz.diamond.persistence.stub.persistenceStub
import nl.uwv.smz.diamond.shared.common.Modules
import nl.uwv.smz.diamond.view.controller_impl.controllerImpl
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

private val log = logger {}

fun Application.installKoin(config: Config) {
    log.info { "installKoin" }
    install(Koin) {
        slf4jLogger()
        modules(Modules.all(config))
    }
}

fun Modules.all(config: Config) = listOf(
    controllerImpl(),
    domainLogicImpl(),
    when (config.database.mode) {
        is PersistenceMode.ImplMode -> persistenceImpl(config.database.mode.impl)
        PersistenceMode.StubMode -> persistenceStub()
    },
)
