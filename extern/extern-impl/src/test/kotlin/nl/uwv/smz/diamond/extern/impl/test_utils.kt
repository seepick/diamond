package nl.uwv.smz.diamond.extern.impl

import io.github.oshai.kotlinlogging.DelegatingKLogger
import io.github.oshai.kotlinlogging.KLogger
import org.slf4j.Logger

/** Convert Oshai to Slf4j logger. */
@Suppress("UNCHECKED_CAST")
fun KLogger.toSlf4j(): Logger = (this as DelegatingKLogger<Logger>).underlyingLogger
