package nl.uwv.smz.diamond.itest.testInfra

import com.sksamuel.hoplite.Masked
import com.sksamuel.hoplite.Secret
import io.cucumber.java.After
import io.cucumber.java.Before
import io.cucumber.java.Scenario
import io.github.oshai.kotlinlogging.KotlinLogging.logger
import io.ktor.client.HttpClient
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
import org.koin.core.Koin
import org.koin.ktor.plugin.KOIN_ATTRIBUTE_KEY
import java.time.LocalDateTime
import java.util.concurrent.atomic.AtomicInteger
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
    fun `before each scenario`(scenario: Scenario) {
        log.trace { "Starting ktor for test: ${scenario.name}" }

        var tmpClient: HttpClient? = null
        startKtor {
            tmpClient = createClient {
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
            client = tmpClient
            application {
                setupDiamondKtor(testGlobalConfiguration, Modules.externStub())
            }
        }
        @Suppress("USELESS_CAST") val koin = testApplication.application.attributes[KOIN_ATTRIBUTE_KEY] as Koin
        world.reinitialize(WorldContext(tmpClient!!, koin))

//        testApplication = TestApplication {
//            // will implictly start ktor's test-engine
//            application {
//                setupDiamondKtor(testGlobalConfiguration, Modules.externStub())
//            }
//        }
//        val testClient = testApplication.client
//        @Suppress("USELESS_CAST") val koin = testApplication.application.attributes[KOIN_ATTRIBUTE_KEY] as Koin
//        world.reinitialize(WorldContext(testClient, koin))
    }

    // TODO report to ktor people, using with cucumber, "delocated" shutdown of ktor test application context
    @Suppress("INVISIBLE_REFERENCE", "ERROR_SUPPRESSION") // FIXME this is a hack :-/
    private fun startKtor(block: suspend ApplicationTestBuilder.() -> Unit) = runBlocking {
        val builder = ApplicationTestBuilder()
        with(builder) {
            withContext(EmptyCoroutineContext) { block() }
        }
        testApplication = builder.testApplication.apply { start() }
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
