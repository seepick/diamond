@file:OptIn(ExperimentalHoplite::class)

package nl.uwv.smz.diamond.app

import com.sksamuel.hoplite.ConfigException
import com.sksamuel.hoplite.ConfigLoaderBuilder
import com.sksamuel.hoplite.ExperimentalHoplite
import com.sksamuel.hoplite.addResourceSource
import nl.uwv.smz.diamond.persistence.impl.DatabaseConfig
import nl.uwv.smz.diamond.shared.config.SubConfig
import java.time.LocalDateTime

data class GlobalConfig(
    val env: EnvConfig,
    val build: BuildProperties,
)

data class EnvConfig(
    @SubConfig val ktor: KtorConfig,
    @SubConfig val database: DatabaseConfig,
)

@OptIn(ExperimentalHoplite::class)
fun readEnvConfig(): EnvConfig = try {
    ConfigLoaderBuilder.default()
        .withExplicitSealedTypes()
        .build()
        .loadConfigOrThrow<EnvConfig>()
} catch (e: ConfigException) {
    throw ConfigException(
        "Application configuration failed. " +
                "Please see exception cause for details and the SAD document for configuration details.",
        e
    )
}

data class BuildProperties(
    val appVersion: String,
    val buildTime: LocalDateTime,
)

private const val BUILD_INJECTED_CLASSPATH = "/buildInjected.properties"

fun readBuildProperties(): BuildProperties =
    ConfigLoaderBuilder.default()
        .withExplicitSealedTypes()
        .addResourceSource(BUILD_INJECTED_CLASSPATH).build()
        .loadConfigOrThrow<BuildProperties>()
