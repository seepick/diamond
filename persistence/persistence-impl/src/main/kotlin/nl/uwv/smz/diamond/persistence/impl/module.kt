package nl.uwv.smz.diamond.persistence.impl

import io.github.oshai.kotlinlogging.KotlinLogging.logger
import nl.uwv.smz.diamond.persistence.api.CrystalRepo
import nl.uwv.smz.diamond.shared.common.Modules
import org.jetbrains.exposed.sql.Database
import org.koin.dsl.module

private val log = logger {}

@Suppress("UnusedReceiverParameter")
fun Modules.persistenceImpl(config: DbConfig) = module {
    val db = connectToDatabase(config)
    single<CrystalRepo> { CrystalExposedDboRepo(db) }
}

data class DbConfig(
    val url: String,
    val username: String,
    val password: String,
) {
    override fun toString(): String {
        return super.toString()
    }
}

internal fun connectToDatabase(config: DbConfig): Database {
    log.info { "Connecting to database" }
    return Database.connect(
        url = config.url,
        user = config.username,
        password = config.password,
    )
}
