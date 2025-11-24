package nl.uwv.smz.diamond.app

import io.github.oshai.kotlinlogging.KotlinLogging.logger
import io.ktor.server.application.Application
import io.ktor.server.application.install
import nl.uwv.smz.diamond.app.PersistenceMode.Impl
import nl.uwv.smz.diamond.app.PersistenceMode.Stub
import nl.uwv.smz.diamond.domain_logic_impl.domainLogicImpl
import nl.uwv.smz.diamond.persistence.impl.DbConfig
import nl.uwv.smz.diamond.persistence.impl.persistenceImpl
import nl.uwv.smz.diamond.persistence.stub.persistenceStub
import nl.uwv.smz.diamond.shared.common.Modules
import nl.uwv.smz.diamond.view.controller_impl.controllerImpl
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

private val log = logger {}

enum class PersistenceMode {
    Impl, Stub
}

fun Application.installKoin(persistenceMode: PersistenceMode) {
    log.info { "installKoin($persistenceMode)" }
    install(Koin) {
        slf4jLogger()
        modules(Modules.all(persistenceMode))
    }
}

fun Modules.all(persistenceMode: PersistenceMode) = listOf(
    controllerImpl(),
    domainLogicImpl(),
    when (persistenceMode) {
        Impl -> persistenceImpl(
            DbConfig(
                // TODO configure properly
                url = "jdbc:h2:mem:maindb${System.currentTimeMillis()};DB_CLOSE_DELAY=-1",
                username = "",
                password = ""
            )
        )

        Stub -> persistenceStub()
    },
)
