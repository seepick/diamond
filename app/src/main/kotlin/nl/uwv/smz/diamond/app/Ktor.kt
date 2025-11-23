package nl.uwv.smz.diamond.app

import io.github.oshai.kotlinlogging.KotlinLogging.logger
import io.ktor.server.application.Application
import io.ktor.server.engine.ApplicationEngine
import io.ktor.server.engine.ApplicationEngineFactory
import io.ktor.server.engine.EmbeddedServer
import io.ktor.server.engine.embeddedServer
import nl.uwv.smz.diamond.view.routing.installDiamondRouting
import nl.uwv.smz.diamond.view.routing.setupFundamentalKtorFeatures

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
    installKoin()
    setupFundamentalKtorFeatures()
    installDiamondRouting()
}

data class KtorConfig(
    val port: Int
)
