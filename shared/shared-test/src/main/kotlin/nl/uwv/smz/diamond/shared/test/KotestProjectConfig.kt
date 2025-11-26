package nl.uwv.smz.diamond.shared.test

import io.kotest.core.config.AbstractProjectConfig
import io.kotest.core.config.LogLevel
import io.kotest.core.names.DuplicateTestNameMode

/**
 * Programmatically rewiring logback config (more control than via XML in classpath).
 *
 * Kotest before project/all needs to be set via this kind of service locator, not possible otherwise.
 * Needs to be set as a system property in Gradle, see: diamond-kotlin-test.gradle.kts
 * Attention: There can be only one global project config be registered!
 * See: https://kotest.io/docs/framework/project-config.html
 */
object DiamondKotestProjectConfig : AbstractProjectConfig() {

    // TestExecutionMode concurrent
    override val logLevel = LogLevel.Error
    override val duplicateTestNameMode = DuplicateTestNameMode.Error
//    override val isolationMode = IsolationMode.SingleInstance

    override suspend fun beforeProject() {
        reconfigureLogForTest()
    }

    // TODO what's that?
//    @OptIn(ExperimentalKotest::class)
//    override val extensions = listOf(
//        object : LogExtension {
//            override suspend fun handleLogs(testCase: TestCase, logs: List<LogEntry>) {
//                logs.forEach { println("FOO: " + it.level.name + " - " + it.message) }
//            }
//        }
//    )
}
