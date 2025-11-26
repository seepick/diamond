package nl.uwv.smz.diamond.persistence.impl

import com.sksamuel.hoplite.Masked
import nl.uwv.smz.diamond.shared.config.ConfigProperty

data class DatabaseConfig(
    @ConfigProperty("JDBC driver URL")
    val url: String,
    @ConfigProperty("DB username")
    val username: String,
    @ConfigProperty("DB password")
    val password: Masked,
)
