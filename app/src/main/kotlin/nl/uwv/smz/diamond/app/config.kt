package nl.uwv.smz.diamond.app

import com.sksamuel.hoplite.ConfigException
import com.sksamuel.hoplite.ConfigLoaderBuilder
import com.sksamuel.hoplite.ExperimentalHoplite
import nl.uwv.smz.diamond.persistence.impl.DatabaseConfig
import nl.uwv.smz.diamond.shared.config.SubConfig

@OptIn(ExperimentalHoplite::class)
fun readConfig(): Config = try {
    ConfigLoaderBuilder.default().withExplicitSealedTypes().build().loadConfigOrThrow<Config>()
} catch (e: ConfigException) {
    throw ConfigException(
        "Application configuration failed. " +
                "Please see exception cause for details and the SAD document for configuration details.",
        e
    )
}

data class Config(
    @SubConfig val server: ServerConfig,
    @SubConfig val database: DatabaseConfig,
)
