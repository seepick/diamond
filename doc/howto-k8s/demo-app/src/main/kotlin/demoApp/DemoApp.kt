package demoApp

import demoApp.db.*
import io.github.oshai.kotlinlogging.KotlinLogging.logger
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.jetbrains.exposed.sql.Database

object DemoApp {

    private val log = logger {}

    init {
        log.info { "DemoApp starting up." }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val config = AppConfig.load()
        log.info { "Starting up application with: $config" }
//        setupDb(config)

        embeddedServer(Netty, port = config.ktorPort) {
            startKtor()
        }.start(wait = true)
    }

    private fun setupDb(config: AppConfig) {
        val dbAccess = DatabaseAccess(config.dbJdbc, config.dbUser, config.dbPass)
        log.info { "Connecting and migrating: $dbAccess" }
        LiquibaseMigrator.migrate(dbAccess)
        Database.connect(dbAccess)
    }
}
