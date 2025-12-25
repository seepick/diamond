package demoApp.db

import io.github.oshai.kotlinlogging.KotlinLogging.logger
import liquibase.command.CommandScope
import liquibase.command.core.UpdateCommandStep
import liquibase.command.core.helpers.DbUrlConnectionArgumentsCommandStep
import liquibase.database.DatabaseFactory
import liquibase.database.jvm.JdbcConnection
import java.sql.DriverManager
import kotlin.use

data class DatabaseAccess(
    val jdbcUrl: String,
    val username: String,
    val password: String,
) {
    override fun toString() = "DatabaseAccess[jdbcUrl=$jdbcUrl; username=$username; password=****]"
}

object LiquibaseMigrator {

    private const val CHANGELOG_CLASSPATH = "/liquibase.xml"
    private val log = logger {}

    fun migrate(access: DatabaseAccess) {
        log.info { "Migrating database: [${access.jdbcUrl}]" }
        DriverManager.getConnection(access.jdbcUrl, access.username, access.password).use { connection ->
            val database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(JdbcConnection(connection))

            @Suppress("SpreadOperator")
            val updateCommand = CommandScope(*UpdateCommandStep.COMMAND_NAME)
            updateCommand.addArgumentValue(DbUrlConnectionArgumentsCommandStep.DATABASE_ARG, database)
            updateCommand.addArgumentValue(UpdateCommandStep.CHANGELOG_FILE_ARG, CHANGELOG_CLASSPATH)
            updateCommand.execute()
        }
        log.info { "Migrating database done ✅" }
    }
}
