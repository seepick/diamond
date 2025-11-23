package nl.uwv.smz.diamond.persistence.impl

import io.kotest.core.listeners.AfterEachListener
import io.kotest.core.listeners.BeforeEachListener
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction


val allTables = arrayOf(
    CrystalTable
)

object DbListener : BeforeEachListener, AfterEachListener {

    private lateinit var db: Database

    // see: https://github.com/JetBrains/Exposed/issues/726 ... for SQLite only
//    private lateinit var keepAliveConnection: Connection

    override suspend fun beforeEach(testCase: TestCase) {
        val jdbcUrl = testJdbcInmemoryUrl()
        db = Database.connect(jdbcUrl) //, setupConnection = ::enableSqliteForeignKeySupport)
//        keepAliveConnection = DriverManager.getConnection(jdbcUrl)
        transaction(db) {
            SchemaUtils.create(*allTables)
        }
    }

    override suspend fun afterEach(testCase: TestCase, result: TestResult) {
        TransactionManager.closeAndUnregister(db)
//        keepAliveConnection.close()
    }
}

fun testJdbcInmemoryUrl(): String =
    "jdbc:h2:mem:testdb${System.currentTimeMillis()};DB_CLOSE_DELAY=-1" // DB_CLOSE_DELAY otherwise gone  (also "DB_CLOSE_ON_EXIT=FALSE"?)
//    "jdbc:h2:file:test${System.currentTimeMillis()}?mode=memory&cache=shared"
