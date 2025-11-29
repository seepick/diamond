package nl.uwv.smz.diamond.persistence.impl.testInfra

import io.github.oshai.kotlinlogging.KotlinLogging.logger
import io.kotest.core.listeners.AfterProjectListener
import io.kotest.core.listeners.BeforeSpecListener
import io.kotest.core.listeners.BeforeTestListener
import io.kotest.core.spec.Spec
import io.kotest.core.test.TestCase
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import nl.uwv.smz.diamond.persistence.impl.DatabaseAccess
import nl.uwv.smz.diamond.persistence.impl.LiquibaseMigrator
import nl.uwv.smz.diamond.persistence.impl.connect
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.testcontainers.oracle.OracleContainer
import java.time.Duration

class TestcontainersDbListener : DbListener, BeforeSpecListener, BeforeTestListener, AfterProjectListener {

    private val log = logger {}
    private var oracle: OracleContainer? = null
    override lateinit var database: Database
    private val mutex = Mutex()

    override suspend fun beforeSpec(spec: Spec) {
        mutex.withLock {
            val currentOracle = oracle // to enable smart-cast
            if (currentOracle == null || !currentOracle.isRunning) {
                startOracle()
            }
        }
    }

    private fun startOracle() {
        log.info { "Starting Oracle testcontainers" }
        oracle = createOracleContainer().apply { start() }
        val dbAccess = oracle!!.toDatabaseAccess()
        LiquibaseMigrator.migrate(dbAccess)
        database = Database.connect(dbAccess)
    }

    override suspend fun afterProject() {
        log.info { "Stopping Oracle testcontainers" }
        val currentOracle = oracle // to enable smart-cast
        if (currentOracle != null && currentOracle.isRunning) {
            currentOracle.stop()
            oracle = null
        }
    }

    override suspend fun beforeTest(testCase: TestCase) {
        log.info { "Delete all content from DB tables" }
        transaction(database) {
            allTables.forEach { table ->
                table.deleteAll()
            }
        }
    }
}

private fun createOracleContainer() =
    OracleContainer("gvenzl/oracle-free:23.5-slim-faststart")
        .withStartupTimeout(Duration.ofMinutes(2))
        .withPassword("test_password")
        .withDatabaseName("test_db")

private fun OracleContainer.toDatabaseAccess() = DatabaseAccess(
    jdbcUrl = jdbcUrl,
    username = username,
    password = password,
)
