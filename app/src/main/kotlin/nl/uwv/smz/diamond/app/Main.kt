package nl.uwv.smz.diamond.app

import io.github.oshai.kotlinlogging.KotlinLogging.logger
import io.ktor.server.netty.Netty
import nl.uwv.smz.diamond.shared.logging.LogLevel
import nl.uwv.smz.diamond.shared.logging.reconfigureLogback

object Main {

    @JvmStatic
    fun main(args: Array<String>) {
        reconfigureLog()
        val log = logger {}
        log.info { "Starting application and wait..." }

        Ktor
            .prepare(KtorConfig(port = 8000), Netty)
            .start(wait = true)
    }

    private fun reconfigureLog() {
        reconfigureLogback {
            rootLevel = LogLevel.Warn
            addConsoleAppender {
                pattern = "%d{HH:mm:ss.SSS} [%-5level] %logger{42} - %msg%n"
            }
            packageLevel(LogLevel.Trace, Main::class.java.packageName)
        }
    }
}
