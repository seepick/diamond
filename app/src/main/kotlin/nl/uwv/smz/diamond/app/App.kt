package nl.uwv.smz.diamond.app

import io.github.oshai.kotlinlogging.KotlinLogging.logger
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import nl.uwv.smz.diamond.view.installRouting

object App {
    // TODO programmatic log configuration?!
    private val log = logger {}

    @JvmStatic
    fun main(args: Array<String>) {
        startKtor()
    }

    fun startKtor() {
        log.info { "Starting Ktor ..." }
        embeddedServer(Netty, port = 8000) {
            installRouting()
        }.start(wait = true)
    }
}
