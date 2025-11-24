package nl.uwv.smz.diamond.app

import io.github.oshai.kotlinlogging.KotlinLogging.logger
import io.ktor.server.netty.Netty
import nl.uwv.smz.diamond.shared.common.Constants
import nl.uwv.smz.diamond.shared.logging.LogLevel
import nl.uwv.smz.diamond.shared.logging.reconfigureLogback

/** FQN has to be in sync with /app/build.gradle.kts main class definition. */
object DiamondApp {

    @JvmStatic
    fun main(args: Array<String>) {
        reconfigureLog()
        val log = logger {}
        log.info { "Starting application and wait..." }

        Ktor
            .prepare(KtorConfig(port = 8000), Netty, PersistenceMode.Impl)
            .start(wait = true)
    }

    private fun reconfigureLog() {
        // TODO rolling file appender for PROD (or via app-config property?!)
        reconfigureLogback {
            rootLevel = LogLevel.Warn
            addConsoleAppender {
                pattern = "%d{HH:mm:ss.SSS} [%-5level] %logger{42} - %msg%n"
            }
            packageLevel(LogLevel.Trace, Constants.ROOT_PACKAGE_NAME)
        }
    }
}
