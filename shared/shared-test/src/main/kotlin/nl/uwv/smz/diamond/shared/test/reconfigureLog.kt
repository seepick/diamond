package nl.uwv.smz.diamond.shared.test

import nl.uwv.smz.diamond.shared.common.Constants
import nl.uwv.smz.diamond.shared.logging.LogLevel
import nl.uwv.smz.diamond.shared.logging.reconfigureLogback

fun reconfigureLogForTest() {
    println("[DIAMOND] Reconfiguring logback for test purpose")
    reconfigureLogback {
        rootLevel = LogLevel.Warn
        packageLevel(LogLevel.Trace, Constants.ROOT_PACKAGE_NAME)
        addConsoleAppender {
            // important to have the thread name here for parallel test execution debugging
            pattern = "%-43(%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread]) [%-5level] %logger{42} - %msg%n"
        }
    }
}
