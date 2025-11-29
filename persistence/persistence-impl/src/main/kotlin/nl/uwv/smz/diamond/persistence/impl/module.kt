package nl.uwv.smz.diamond.persistence.impl

import io.github.oshai.kotlinlogging.KotlinLogging.logger
import nl.uwv.smz.diamond.persistence.api.CrystalRepo
import nl.uwv.smz.diamond.shared.common.Modules
import org.jetbrains.exposed.sql.Database
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

private val log = logger {}

@Suppress("UnusedReceiverParameter")
fun Modules.persistenceImpl(config: DatabaseConfig) = module {
    single<Database> {
        // TODO deal properly with connection fails java.sql.SQLException
        // TODO shall we really delay connection here?!
        // but then also checking connection in health endpoint... hm...
        connectToDatabase(config)
    }
    singleOf(::CrystalExposedDboRepo) { bind<CrystalRepo>() }
}

private fun connectToDatabase(config: DatabaseConfig): Database {
    log.info { "Connecting to database" }
    val access = config.toDatabaseAccess()
    LiquibaseMigrator.migrate(access)
    return Database.connect(access)
}
