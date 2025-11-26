package nl.uwv.smz.diamond.app

import com.sksamuel.hoplite.ConfigLoaderBuilder
import com.sksamuel.hoplite.ExperimentalHoplite
import nl.uwv.smz.diamond.persistence.impl.DatabaseConfig
import nl.uwv.smz.diamond.shared.config.ConfigProperty
import nl.uwv.smz.diamond.shared.config.SubConfig

@OptIn(ExperimentalHoplite::class)
fun readConfig(): Config =
    ConfigLoaderBuilder.default().withExplicitSealedTypes().build().loadConfigOrThrow<Config>()

data class Config(
    @SubConfig
    val database: DatabaseMetaConfig,
    @SubConfig
    val ktor: KtorConfig,
)

data class DatabaseMetaConfig(
    @ConfigProperty("Switch between in-memory stub and real implementation mode")
    val mode: PersistenceMode, // FIXME config parser doesn't pick it up :-(
)

sealed interface PersistenceMode {
    object StubMode : PersistenceMode
    data class ImplMode(
        val impl: DatabaseConfig
    ) : PersistenceMode
}
