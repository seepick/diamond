package nl.uwv.smz.diamond.app

import com.sksamuel.hoplite.Masked
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.equals.shouldBeEqual
import nl.uwv.smz.diamond.persistence.impl.DatabaseConfig

/** See `/local/test_config.sh` for usage how it is actually being done in the end. */
class ConfigTest : StringSpec({
    "Given system properties When read config Then properly parsed" {
        System.setProperty("config.override.database.jdbcUrl", "a")
        System.setProperty("config.override.database.username", "b")
        System.setProperty("config.override.database.password", "c")
        System.setProperty("config.override.ktor.port", "12")

        readEnvConfig() shouldBeEqual EnvConfig(
            ktor = KtorConfig(
                port = 12,
            ),
            database = DatabaseConfig(
                jdbcUrl = "a",
                username = "b",
                password = Masked("c"),
            ),
        )
    }
})
