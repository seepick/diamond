package nl.uwv.smz.diamond.persistence.impl

import io.github.oshai.kotlinlogging.KotlinLogging.logger
import nl.uwv.smz.diamond.persistence.api.CrystalRepo
import nl.uwv.smz.diamond.shared.common.Modules
import org.jetbrains.exposed.sql.Database
import org.koin.dsl.module

private val log = logger {}

@Suppress("UnusedReceiverParameter")
fun Modules.persistenceImpl(config: DatabaseConfig) = module {
    single<Database> { connectToDatabase(config) } // delay connection
    single<CrystalRepo> { CrystalExposedDboRepo(get()) }
}

private fun connectToDatabase(config: DatabaseConfig): Database {
    log.info { "Connecting to database" }
    val access = config.toDatabaseAccess()
    LiquibaseMigrator.migrate(access)
    return Database.connect(access)
}
