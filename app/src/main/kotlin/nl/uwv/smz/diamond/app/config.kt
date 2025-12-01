@file:OptIn(ExperimentalHoplite::class)

package nl.uwv.smz.diamond.app

import com.sksamuel.hoplite.ConfigException
import com.sksamuel.hoplite.ConfigLoaderBuilder
import com.sksamuel.hoplite.ExperimentalHoplite
import com.sksamuel.hoplite.addResourceSource
import kotlinx.datetime.LocalDateTime
import nl.uwv.smz.diamond.extern.impl.ExternConfig
import nl.uwv.smz.diamond.persistence.impl.DatabaseConfig
import nl.uwv.smz.diamond.shared.config.SubConfig
import nl.uwv.smz.diamond.view.routing.RoutingSetting

data class GlobalConfiguration(
    /** Read via hoplite from environment variables (or system properties). */
    val env: EnvConfig,
    /** Injected variables from Gradle build. */
    val build: BuildProperties,
    /** Static settings configured only internally via code (prod/test). */
    val routingSetting: RoutingSetting = RoutingSetting(),
)

data class EnvConfig(
    @SubConfig val ktor: KtorConfig,
    @SubConfig val database: DatabaseConfig,
    @SubConfig val extern: ExternConfig,
)

@OptIn(ExperimentalHoplite::class)
fun readEnvConfig(): EnvConfig = try {
    ConfigLoaderBuilder
        .default()
        .withExplicitSealedTypes()
        .build()
        .loadConfigOrThrow<EnvConfig>()
} catch (e: ConfigException) {
    throw ConfigException(
        "Application configuration failed. " +
            "Please see exception cause for details and the SAD document for configuration details.",
        e,
    )
}

/** Injected by Gradle. */
data class BuildProperties(
    val appVersion: String,
    val buildTime: LocalDateTime,
    /** GIT branch currently checked out; e.g. "feature/SMP-12345_foobar */
    val branchName: String,
)

private const val BUILD_INJECTED_CLASSPATH = "/buildInjected.properties"

fun readBuildProperties(): BuildProperties =
    ConfigLoaderBuilder
        .default()
        .withExplicitSealedTypes()
        .addResourceSource(BUILD_INJECTED_CLASSPATH)
        .build()
        .loadConfigOrThrow<BuildProperties>()
