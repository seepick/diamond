package nl.uwv.smz.diamond.persistence.impl

import io.github.oshai.kotlinlogging.KotlinLogging.logger
import nl.uwv.smz.diamond.persistence.api.CrystalRepo
import nl.uwv.smz.diamond.shared.common.Modules
import org.jetbrains.exposed.sql.Database
import org.koin.dsl.module

private val log = logger {}

@Suppress("UnusedReceiverParameter")
fun Modules.persistenceImpl(config: DatabaseConfig) = module {
    val db = connectToDatabase(config)
    single<CrystalRepo> { CrystalExposedDboRepo(db) }
}

internal fun connectToDatabase(config: DatabaseConfig): Database {
    log.info { "Connecting to database" }
    return Database.connect(
        url = config.url,
        user = config.username,
        password = config.password.value,
    )
}
