package nl.uwv.smz.diamond.persistence.impl.testInfra

import io.github.oshai.kotlinlogging.KotlinLogging.logger
import io.kotest.core.listeners.AfterEachListener
import io.kotest.core.listeners.BeforeEachListener
import io.kotest.core.test.TestCase
import io.kotest.engine.test.TestResult
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction

class InmemoryDbListener(
    private val createSchema: Boolean = true
) : DbListener, BeforeEachListener, AfterEachListener {

    private val log = logger {}
    lateinit var jdbcUrl: String
    override lateinit var db: Database

    override suspend fun beforeEach(testCase: TestCase) {
        jdbcUrl = createJdbcInmemoryUrl()
        db = Database.connect(jdbcUrl)
        if (createSchema) {
            log.debug { "Creating complete schema via Exposed." }
            transaction(db) {
                SchemaUtils.create(*allTables)
            }
        }
    }

    override suspend fun afterEach(testCase: TestCase, result: TestResult) {
        TransactionManager.closeAndUnregister(db)
    }
}

private fun createJdbcInmemoryUrl(): String =
    // DB_CLOSE_DELAY otherwise gone
    "jdbc:h2:mem:testdb${System.currentTimeMillis()};DB_CLOSE_DELAY=-1"
