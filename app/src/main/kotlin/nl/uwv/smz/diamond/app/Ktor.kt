package nl.uwv.smz.diamond.app

import io.github.oshai.kotlinlogging.KotlinLogging.logger
import io.ktor.server.application.Application
import io.ktor.server.engine.ApplicationEngine
import io.ktor.server.engine.ApplicationEngineFactory
import io.ktor.server.engine.EmbeddedServer
import io.ktor.server.engine.embeddedServer
import nl.uwv.smz.diamond.shared.config.ConfigProperty
import nl.uwv.smz.diamond.view.routing.installPlugins
import nl.uwv.smz.diamond.view.routing.installRoutings

typealias KtorServer = EmbeddedServer<ApplicationEngine, out ApplicationEngine.Configuration>

object Ktor {

    private val log = logger {}

    fun prepare(
        config: Config,
        factory: ApplicationEngineFactory<*, *>,
    ): KtorServer {
        log.info { "Preparing Ktor with $config" }
        return embeddedServer(factory, port = config.server.port) {
            setupCompleteKtor(config)
        }
    }
}

/** Visible for integration test setup */
fun Application.setupCompleteKtor(config: Config) {
    installKoin(config)
    installPlugins()
    installRoutings()
}

data class ServerConfig(
    @ConfigProperty("Webserver HTTP port to listen to")
    val port: Int = 8080,
)
