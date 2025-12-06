package nl.uwv.smz.diamond.app

import io.github.oshai.kotlinlogging.KotlinLogging.logger
import io.ktor.server.netty.Netty
import nl.uwv.smz.diamond.shared.common.Constants
import nl.uwv.smz.diamond.shared.logging.LogLevel
import nl.uwv.smz.diamond.shared.logging.reconfigureLogback
import org.koin.core.module.Module

/** FQN has to be in sync with /app/build.gradle.kts main class definition. */
object DiamondApp {

    private const val APP_ARG_PRINT_CONFIG_ONLY = "printConfigOnly"

    @JvmStatic
    fun main(args: Array<String>) {
        if (args.contains(APP_ARG_PRINT_CONFIG_ONLY)) {
            println(readEnvConfig())
        } else {
            startApp()
        }
    }

    /** With default params, so LocalDiamondApp can reconfigure the application accordingly. */
    fun startApp(
        defaultLog: () -> Unit = { reconfigureProdLog() },
        defaultEnvConfig: () -> EnvConfig = { readEnvConfig() },
        externStub: Module? = null,
    ) {
        defaultLog()
        val log = logger {}
        val config = GlobalConfiguration(
            env = defaultEnvConfig(),
            build = readBuildProperties(),
        )
        log.info { buildBanner(config.build) }
        Ktor.prepare(config, Netty, externStub).start(wait = true)
    }

    private fun reconfigureProdLog() {
        reconfigureLogback {
            rootLevel = LogLevel.Warn
            addConsoleAppender {
                pattern = "%d{HH:mm:ss.SSS} [%-5level] %logger{42} - %msg%n"
            }
            packageLevel(LogLevel.Trace, Constants.ROOT_PACKAGE_NAME)
        }
    }
}
