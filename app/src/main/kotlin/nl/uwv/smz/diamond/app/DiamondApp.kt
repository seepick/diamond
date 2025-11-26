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
        if (args.contains("printConfigOnly")) {
            println(readConfig())
        } else {
            startApp()
        }
    }

    fun startApp(
        defaultLog: () -> Unit = { reconfigureLog() },
        defaultConfig: () -> Config = { readConfig() },
    ) {
        defaultLog()
        val log = logger {}
        log.info { "Starting application and wait..." }
        val config = defaultConfig()
        Ktor.prepare(config, Netty).start(wait = true)
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
