package nl.uwv.smz.diamond.app

import com.sksamuel.hoplite.Masked
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.equals.shouldBeEqual
import nl.uwv.smz.diamond.extern.impl.ExternConfig
import nl.uwv.smz.diamond.persistence.impl.DatabaseConfig

/** See `/local/test_config.sh` for usage how it is actually being done in the end. */
class ConfigTest : StringSpec({
    "Given system properties When read config Then properly parsed" {
        System.setProperty("config.override.database.jdbcUrl", "dbUrl")
        System.setProperty("config.override.database.username", "dbUser")
        System.setProperty("config.override.database.password", "dbPass")
        System.setProperty("config.override.ktor.port", "42")
        System.setProperty("config.override.extern.postsServiceBaseUrl", "postsUrl")

        // if this test fails, please also adjust the `/local/test_config.sh` file -thank you :)
        readEnvConfig() shouldBeEqual EnvConfig(
            ktor = KtorConfig(
                port = 42,
            ),
            database = DatabaseConfig(
                jdbcUrl = "dbUrl",
                username = "dbUser",
                password = Masked("dbPass"),
            ),
            extern =
                ExternConfig(
                    postsServiceBaseUrl = "postsUrl",
                )
        )
    }
})
