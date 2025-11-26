package nl.uwv.smz.diamond.persistence.impl.testInfra

import io.github.oshai.kotlinlogging.KotlinLogging.logger
import io.kotest.core.listeners.AfterSpecListener
import io.kotest.core.listeners.BeforeSpecListener
import io.kotest.core.listeners.BeforeTestListener
import io.kotest.core.spec.Spec
import io.kotest.core.test.TestCase
import nl.uwv.smz.diamond.persistence.impl.DatabaseAccess
import nl.uwv.smz.diamond.persistence.impl.LiquibaseMigrator
import nl.uwv.smz.diamond.persistence.impl.connect
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.testcontainers.oracle.OracleContainer
import java.time.Duration

class TestcontainersDbListener : DbListener, BeforeTestListener, BeforeSpecListener, AfterSpecListener {

    private val log = logger {}
    private lateinit var oracle: OracleContainer
    override lateinit var db: Database

    override suspend fun beforeSpec(spec: Spec) {
        log.info { "Starting Oracle testcontainers..." }
        oracle = createOracleContainer().apply { start() }
        val dbAccess = oracle.toDatabaseAccess()
        LiquibaseMigrator.migrate(dbAccess)
        db = Database.connect(dbAccess)
    }

    override suspend fun afterSpec(spec: Spec) {
        log.info { "Stopping Oracle testcontainers..." }
        oracle.stop()
    }

    override suspend fun beforeTest(testCase: TestCase) {
        transaction(db) {
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
