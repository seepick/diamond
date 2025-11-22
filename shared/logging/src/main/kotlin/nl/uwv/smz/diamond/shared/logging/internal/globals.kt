package nl.uwv.smz.diamond.shared.logging.internal

import ch.qos.logback.classic.LoggerContext
import org.slf4j.LoggerFactory

internal val context: LoggerContext = LoggerFactory.getILoggerFactory() as LoggerContext
