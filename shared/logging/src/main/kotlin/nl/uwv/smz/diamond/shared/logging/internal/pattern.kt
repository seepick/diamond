package nl.uwv.smz.diamond.shared.logging.internal

import ch.qos.logback.classic.encoder.PatternLayoutEncoder

internal fun patternLayout(pattern: String): PatternLayoutEncoder {
    val layout = PatternLayoutEncoder()
    layout.context = context
    layout.pattern = pattern
    layout.start()
    return layout
}
