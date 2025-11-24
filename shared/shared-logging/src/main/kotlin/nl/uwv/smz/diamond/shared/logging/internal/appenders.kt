package nl.uwv.smz.diamond.shared.logging.internal


import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.ConsoleAppender
import ch.qos.logback.core.rolling.RollingFileAppender
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy
import nl.uwv.smz.diamond.shared.logging.ConsoleAppenderBuilder
import nl.uwv.smz.diamond.shared.logging.FileAppenderBuilder
import nl.uwv.smz.diamond.shared.logging.LogLevel
import java.util.concurrent.atomic.AtomicInteger

private val appenderCounter = AtomicInteger(1)
private const val DEFAULT_PATTERN = "%-43(%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread]) [%-5level] %logger{42} - %msg%n"

internal data class InternalConsoleAppenderBuilder(
    override var appenderName: String = "LogbackConsoleAppender_" + appenderCounter.getAndIncrement(),
    override var pattern: String = DEFAULT_PATTERN,
    override var level: LogLevel = LogLevel.Trace
) : ConsoleAppenderBuilder {

    internal fun build() = ConsoleAppender<ILoggingEvent>().also {
        it.context = context
        it.name = appenderName
        it.encoder = patternLayout(pattern)
        it.start()
        it.addFilter(ThresholdFilter(level.logbackLevel))
    }
}

internal data class InternalFileAppenderBuilder(
    override var file: String,
    override var filePattern: String,
    override var appenderName: String = "LogbackFileAppender_" + appenderCounter.getAndIncrement(),
    override var pattern: String = DEFAULT_PATTERN,
    override var level: LogLevel = LogLevel.Trace,
    override var maxHistory: Int = 10
) : FileAppenderBuilder {

    internal fun build() = RollingFileAppender<ILoggingEvent>().also { appender ->
        appender.context = context
        appender.name = appenderName
        appender.encoder = patternLayout(pattern)
        appender.file = file
        appender.isAppend = true
        appender.isImmediateFlush = true
        appender.rollingPolicy = TimeBasedRollingPolicy<ILoggingEvent>().also { policy ->
            policy.context = context
            policy.setParent(appender)
            policy.fileNamePattern = filePattern
            policy.maxHistory = maxHistory
            policy.start()
        }

        appender.start()
        appender.addFilter(ThresholdFilter(level.logbackLevel))
    }
}
