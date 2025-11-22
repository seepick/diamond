package nl.uwv.smz.diamond.shared.logging.internal

import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.Appender
import nl.uwv.smz.diamond.shared.logging.ConsoleAppenderBuilder
import nl.uwv.smz.diamond.shared.logging.FileAppenderBuilder
import nl.uwv.smz.diamond.shared.logging.LogLevel
import nl.uwv.smz.diamond.shared.logging.LogbackConfig

internal data class InternalLogbackConfig(
    override var rootLevel: LogLevel = LogLevel.Trace
) : LogbackConfig {

    internal val packageLevels = mutableMapOf<String, LogLevel>()

    internal val appenders = mutableListOf<Appender<ILoggingEvent>>()

    @Suppress("KDocMissingDocumentation")
    override fun packageLevel(level: LogLevel, vararg packageNames: String) {
        packageNames.forEach { packageLevels += it to level }
    }

    @Suppress("KDocMissingDocumentation")
    override fun packageLevel(level: LogLevel, packageNames: List<String>) {
        packageNames.forEach { packageLevels += it to level }
    }

    @Suppress("KDocMissingDocumentation")
    // or advanced via directly: withAppender: ((ConsoleAppender<ILoggingEvent>) -> Unit) = {}
    override fun addConsoleAppender(withBuilder: ConsoleAppenderBuilder.() -> Unit) {
        appenders += InternalConsoleAppenderBuilder().let { builder ->
            withBuilder(builder)
            builder.build()
        }
    }

    override fun addFileAppender(file: String, filePattern: String, withBuilder: FileAppenderBuilder.() -> Unit) {
        appenders += InternalFileAppenderBuilder(file = file, filePattern = filePattern).let { builder ->
            withBuilder(builder)
            builder.build()
        }
    }
}
