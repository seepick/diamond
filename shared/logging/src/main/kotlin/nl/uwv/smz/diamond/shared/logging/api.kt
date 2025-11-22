package nl.uwv.smz.diamond.shared.logging

import ch.qos.logback.classic.LoggerContext
import nl.uwv.smz.diamond.shared.logging.internal.InternalLogbackConfig
import nl.uwv.smz.diamond.shared.logging.internal.context
import org.slf4j.Logger

/**
 * Core API object to configure logback programmatically with a nice DSL.
 */
fun reconfigureLogback(withConfig: LogbackConfig.() -> Unit) { // vararg appenders: Appender<ILoggingEvent>
    // context.statusManager.add(InfoStatus("Setting up log configuration.", context))
    val rootLogger = context.getLogger(Logger.ROOT_LOGGER_NAME)
    rootLogger.detachAndStopAllAppenders()

    val config = InternalLogbackConfig()
    withConfig(config)

    rootLogger.level = config.rootLevel.logbackLevel
    config.packageLevels.forEach { (packageName, level) -> context.changeLevel(packageName, level) }
    config.appenders.forEach(rootLogger::addAppender)
}

private fun LoggerContext.changeLevel(packageName: String, level: LogLevel) {
    getLogger(packageName).level = level.logbackLevel
}
