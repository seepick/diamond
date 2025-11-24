package nl.uwv.smz.diamond.shared.test

import io.kotest.core.config.AbstractProjectConfig
import io.kotest.core.config.LogLevel
import io.kotest.core.names.DuplicateTestNameMode

// see: https://kotest.io/docs/framework/project-config.html
object DiamondKotestProjectConfig : AbstractProjectConfig() {

    // TestExecutionMode concurrent
    override val logLevel = LogLevel.Error
    override val duplicateTestNameMode = DuplicateTestNameMode.Error

    override suspend fun beforeProject() {
        reconfigureLogForTest()
    }

//    @OptIn(ExperimentalKotest::class)
//    override val extensions = listOf(
//        object : LogExtension {
//            override suspend fun handleLogs(testCase: TestCase, logs: List<LogEntry>) {
//                logs.forEach { println("FOO: " + it.level.name + " - " + it.message) }
//            }
//        }
//    )
}
