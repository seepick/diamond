package nl.uwv.smz.diamond.itest.testInfra

import com.sksamuel.hoplite.Masked
import com.sksamuel.hoplite.Secret
import io.cucumber.java.After
import io.cucumber.java.Before
import io.cucumber.java.Scenario
import io.github.oshai.kotlinlogging.KotlinLogging.logger
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.testing.ApplicationTestBuilder
import io.ktor.server.testing.TestApplication
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import nl.uwv.smz.diamond.app.BuildProperties
import nl.uwv.smz.diamond.app.EnvConfig
import nl.uwv.smz.diamond.app.GlobalConfiguration
import nl.uwv.smz.diamond.app.KtorConfig
import nl.uwv.smz.diamond.app.setupDiamondKtor
import nl.uwv.smz.diamond.extern.api.sftp.SftpConfig
import nl.uwv.smz.diamond.extern.impl.ExternConfig
import nl.uwv.smz.diamond.extern.stub.externStub
import nl.uwv.smz.diamond.itest.world.World
import nl.uwv.smz.diamond.itest.world.WorldContext
import nl.uwv.smz.diamond.persistence.impl.DatabaseConfig
import nl.uwv.smz.diamond.shared.common.Modules
import nl.uwv.smz.diamond.view.routing.RoutingSetting
import org.koin.ktor.ext.getKoin
import java.time.LocalDateTime
import java.util.concurrent.atomic.AtomicInteger
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.coroutines.EmptyCoroutineContext

// FIXME if tests run in parallel, world has to be scoped for each test!
class KtorHooks(private val world: World) {

    private val log = logger {}
    private lateinit var testApplication: TestApplication

    private val testEnvConfig = EnvConfig(
        KtorConfig(),
        DatabaseConfig(
            jdbcUrl = "jdbc:h2:mem:testdb${dbCounter.getAndIncrement()};DB_CLOSE_DELAY=-1",
            username = "",
            password = Masked(""),
        ),
        ExternConfig(
            postsServiceBaseUrl = "postsUrl",
            sftp = SftpConfig("", 22, "", true, Secret(""), ""),
        ), // not used, as using stub
    )
    private val testGlobalConfiguration = GlobalConfiguration(
        testEnvConfig,
        BuildProperties(
            appVersion = "0-test",
            buildTime = LocalDateTime.of(2000, 1, 1, 12, 42),
            branchName = "branch/test",
        ),
        RoutingSetting(prettyPrint = true),
    )

    // TODO could replace hooks to java8 lambdas
    @Before
    fun `using hack works`(scenario: Scenario) {
        log.trace { "Starting ktor for test: ${scenario.name}" }
        testApplication = hackTestApplication {
            client = createClient {
                install(Logging) {
                    level = LogLevel.ALL
                }
                install(ContentNegotiation) {
                    json(
                        Json {
                            prettyPrint = true
                            isLenient = false
                            ignoreUnknownKeys = false
                        },
                    )
                }
            }
            application {
                setupDiamondKtor(testGlobalConfiguration, Modules.externStub())
            }
        }
        println("before")
        runBlocking {
            testApplication.start()
        }
        println("after")
        println("attrs: ${testApplication.application.attributes.allKeys}")

        val koin = testApplication.application.getKoin()
//        val koin = testApplication.application.attributes[KOIN_ATTRIBUTE_KEY]
        world.reinitialize(WorldContext(testApplication.client, koin))
    }

    //    @Before
    fun `fails with test http client`() { // no marshalling instaled
        testApplication = TestApplication {
            // will implictly start ktor's test-engine
            application {
                setupDiamondKtor(testGlobalConfiguration, Modules.externStub())
            }
        }
//        testApplication.client = testApplication.createClient {
//
//        }
        runBlocking {
            testApplication.start() // so koin is available!
        }
        println("attrs: ${testApplication.application.attributes.allKeys}")
//        val koin = testApplication.application.attributes[KOIN_ATTRIBUTE_KEY]
        val koin = testApplication.application.getKoin()
        world.reinitialize(WorldContext(testApplication.client, koin))
    }

    // TODO report to ktor people, using with cucumber, "delocated" shutdown of ktor test application context
    @OptIn(ExperimentalContracts::class)
    @Suppress("INVISIBLE_REFERENCE") // FIXME this is a hack :-/
    private fun hackTestApplication(block: suspend ApplicationTestBuilder.() -> Unit): TestApplication {
        contract {
            callsInPlace(block, InvocationKind.EXACTLY_ONCE)
        }
        return runBlocking {
            val builder = ApplicationTestBuilder()
            with(builder) {
                withContext(EmptyCoroutineContext) { block() }
            }
            builder.testApplication
        }
    }

    @After
    fun `after each scenario`(scenario: Scenario): Unit =
        runBlocking {
            log.trace { "stop ktor for: ${scenario.name}" }
            testApplication?.stop()
        }

    companion object {
        private val dbCounter = AtomicInteger(1)
    }
}
