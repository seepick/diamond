package nl.uwv.smz.diamond.app

import io.github.oshai.kotlinlogging.KotlinLogging.logger
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationStarted
import io.ktor.server.engine.ApplicationEngine
import io.ktor.server.engine.ApplicationEngineFactory
import io.ktor.server.engine.EmbeddedServer
import io.ktor.server.engine.embeddedServer
import nl.uwv.smz.diamond.shared.config.ConfigProperty
import nl.uwv.smz.diamond.view.routing.installRoutingsAndPlugins
import java.time.Duration
import java.time.LocalDateTime

typealias KtorServer = EmbeddedServer<ApplicationEngine, out ApplicationEngine.Configuration>

object Ktor {

    private val log = logger {}

    fun prepare(
        config: GlobalConfig,
        factory: ApplicationEngineFactory<*, *>,
    ): KtorServer {
        log.info { "Preparing Ktor at port: ${config.env.ktor.port}" }
        val startTime = LocalDateTime.now()
        return embeddedServer(factory, port = config.env.ktor.port) {
            monitor.subscribe(ApplicationStarted) {
                log.info {
                    "Application successfully finished starting after: ${
                        Duration.between(startTime, LocalDateTime.now()).toSeconds()
                    }sec 👍🏻😎💎"
                }
            }
            setupDiamondKtor(config)
        }
    }
}

/** Visible for integration test setup */
fun Application.setupDiamondKtor(config: GlobalConfig) {
    installKoin(config)
    installRoutingsAndPlugins()
}

data class KtorConfig(
    @ConfigProperty("Webserver HTTP port to listen to")
    val port: Int = 8080,
)
