package nl.uwv.smz.diamond.itest

import com.sksamuel.hoplite.Masked
import io.cucumber.java.After
import io.cucumber.java.Before
import io.cucumber.java.Scenario
import io.github.oshai.kotlinlogging.KotlinLogging.logger
import io.ktor.server.testing.ApplicationTestBuilder
import io.ktor.server.testing.TestApplication
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import nl.uwv.smz.diamond.app.Config
import nl.uwv.smz.diamond.app.ServerConfig
import nl.uwv.smz.diamond.app.setupCompleteKtor
import nl.uwv.smz.diamond.persistence.impl.DatabaseConfig
import kotlin.coroutines.EmptyCoroutineContext

class KtorHooks(private val world: World) {

    private val log = logger {}
    private var testApplication: TestApplication? = null
    private val testConfig = Config(
        ServerConfig(),
        DatabaseConfig(
            jdbcUrl = "jdbc:h2:mem:testdb${System.currentTimeMillis()};DB_CLOSE_DELAY=-1",
            username = "",
            password = Masked(""),
        ),
    )

    @Before
    fun `before each scenario`(scenario: Scenario) {
        log.trace { "Starting ktor for test: ${scenario.name}" }
        startKtor {
            world.initClient(client)
            application {
                setupCompleteKtor(testConfig)
            }
        }
    }

    @Suppress("INVISIBLE_REFERENCE") // FIXME this is a hack :-/
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
