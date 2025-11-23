package nl.uwv.smz.diamond.app

import io.ktor.server.application.Application
import io.ktor.server.application.install
import nl.uwv.smz.diamond.domain_logic_impl.domainLogicImpl
import nl.uwv.smz.diamond.persistence.stub.persistenceStub
import nl.uwv.smz.diamond.shared.common.Modules
import nl.uwv.smz.diamond.view.controller_impl.controllerImpl
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.installKoin() {
    install(Koin) {
        slf4jLogger()
        modules(Modules.all())
    }
}

fun Modules.all() = listOf(
    controllerImpl(),
    domainLogicImpl(),
    persistenceStub(), // TODO make configurable for persistence-stub/impl
)
