package nl.uwv.smz.diamond.itest

import io.cucumber.java.After
import io.cucumber.java.Before
import io.cucumber.java.Scenario
import io.github.oshai.kotlinlogging.KotlinLogging.logger
import io.ktor.server.testing.ApplicationTestBuilder
import io.ktor.server.testing.TestApplication
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import nl.uwv.smz.diamond.app.PersistenceMode
import nl.uwv.smz.diamond.app.setupCompleteKtor
import kotlin.coroutines.EmptyCoroutineContext

class KtorHooks(private val world: World) {

    private val log = logger {}
    private var testApplication: TestApplication? = null

    @Before
    fun `before each scenario`(scenario: Scenario) {
        log.trace { "start ktor for: ${scenario.name}" }

        startKtor {
            world.initClient(client)
            application {
                setupCompleteKtor(PersistenceMode.Impl)
            }
        }
    }

    @Suppress("INVISIBLE_REFERENCE")
    private fun startKtor(block: suspend ApplicationTestBuilder.() -> Unit) = runBlocking {
        val builder = ApplicationTestBuilder()
        with(builder) {
            withContext(EmptyCoroutineContext) { block() }
        }
        testApplication = builder.testApplication.also {
            it.start()
        }
    }

    @After
    fun `after each scenario`(scenario: Scenario): Unit = runBlocking {
        log.trace { "stop ktor for: ${scenario.name}" }
        testApplication?.stop()
    }
}
