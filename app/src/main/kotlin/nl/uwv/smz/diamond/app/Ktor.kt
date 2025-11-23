package nl.uwv.smz.diamond.app

import io.github.oshai.kotlinlogging.KotlinLogging.logger
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.engine.ApplicationEngine
import io.ktor.server.engine.ApplicationEngineFactory
import io.ktor.server.engine.EmbeddedServer
import io.ktor.server.engine.embeddedServer
import nl.uwv.smz.diamond.domain_logic_impl.domainLogicImpl
import nl.uwv.smz.diamond.shared.common.Modules
import nl.uwv.smz.diamond.view.controller_impl.controllerImpl
import nl.uwv.smz.diamond.view.routing.installDiamondRouting
import nl.uwv.smz.diamond.view.routing.setupFundamentalKtorFeatures
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

typealias KtorServer = EmbeddedServer<ApplicationEngine, out ApplicationEngine.Configuration>

object Ktor {

    private val log = logger {}

    fun prepare(
        config: KtorConfig,
        factory: ApplicationEngineFactory<*, *>
    ): KtorServer {
        log.info { "Preparing Ktor with $config" }
        return embeddedServer(factory, port = config.port) {
            setupCompleteKtor()
        }
    }
}

/** Visible for integration test setup */
fun Application.setupCompleteKtor() {
    install(Koin) {
        slf4jLogger()
        modules(Modules.all())
    }
    setupFundamentalKtorFeatures()
    installDiamondRouting()
}

data class KtorConfig(
    val port: Int
)

fun Modules.all() = listOf(
    controllerImpl(),
    domainLogicImpl(),
)
