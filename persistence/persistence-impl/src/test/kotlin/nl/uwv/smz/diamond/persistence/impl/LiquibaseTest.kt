package nl.uwv.smz.diamond.persistence.impl

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContain
import nl.uwv.smz.diamond.persistence.impl.testInfra.InmemoryDbListener
import org.jetbrains.exposed.sql.transactions.transaction

class LiquibaseTest : StringSpec({
    val dbListener = InmemoryDbListener(createSchema = false)
    extension(dbListener)

    "When migrate Then table existing" {
        LiquibaseMigrator.migrate(
            DatabaseAccess(
                jdbcUrl = dbListener.jdbcUrl,
                username = "",
                password = "",
            )
        )
        val tableNames = mutableSetOf<String>()
        transaction(dbListener.database) {
            exec("SELECT * FROM INFORMATION_SCHEMA.TABLES") { rs ->
                while (rs.next()) {
                    tableNames += rs.getString("TABLE_NAME")
                }
            }
        }
        tableNames shouldContain CrystalTable.tableName
    }
})
