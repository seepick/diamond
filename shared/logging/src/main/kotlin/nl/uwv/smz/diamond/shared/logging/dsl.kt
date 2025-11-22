package nl.uwv.smz.diamond.shared.logging

import ch.qos.logback.classic.Level
import ch.qos.logback.core.ConsoleAppender
import ch.qos.logback.core.FileAppender

@DslMarker
internal annotation class Logback4kMarker

/**
 * Supported logback configuration options.
 */
@Logback4kMarker
interface LogbackConfig {
    /** Change the global log level for all appenders and packages. */
    var rootLevel: LogLevel

    /** Change the log level for specific package names*/
    fun packageLevel(level: LogLevel, vararg packageNames: String)

    /** Change the log level for specific package names*/
    fun packageLevel(level: LogLevel, packageNames: List<String>)

    /**
     * Enter nested DSL to create a new appender logging to std out.
     */
    fun addConsoleAppender(withBuilder: ConsoleAppenderBuilder.() -> Unit = {})

    /**
     * Enter nested DSL to create a new appender logging to a (rolling) file.
     *
     * @param file Path to target log file, e.g.: /var/log4k/myapplication.log
     * @param filePattern Path pattern to rolling file, e.g.: /var/log4k/myapplication.%d{yyyy-MM-dd}.log
     */
    fun addFileAppender(
        file: String,
        filePattern: String,
        withBuilder: FileAppenderBuilder.() -> Unit = {}
    )
}

enum class LogLevel(internal val logbackLevel: Level) {
    Trace(Level.TRACE),
    Debug(Level.DEBUG),
    Info(Level.INFO),
    Warn(Level.WARN),
    Error(Level.ERROR);
}

/**
 * Write to console using logback's [ConsoleAppender] class.
 */
@Logback4kMarker
interface ConsoleAppenderBuilder {

    var appenderName: String

    /** Message pattern. */
    var pattern: String

    var level: LogLevel

}

/**
 * Write to a file using logback's [FileAppender] class.
 */
@Logback4kMarker
interface FileAppenderBuilder {

    /**
     * Path to target log file.
     *
     * E.g.: /var/log4k/myapplication.log
     */
    var file: String

    /**
     * Path to target log file.
     *
     * E.g.: /var/log4k/myapplication.%d{yyyy-MM-dd}.log
     */
    var filePattern: String

    var appenderName: String

    /** Message pattern. */
    var pattern: String

    var level: LogLevel

    /**
     * How many instances for [filePattern] are to be persisted.
     */
    var maxHistory: Int

}
