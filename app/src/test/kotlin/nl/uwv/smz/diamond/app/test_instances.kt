package nl.uwv.smz.diamond.app

import com.sksamuel.hoplite.Masked
import io.kotest.property.Arb
import io.kotest.property.arbitrary.Codepoint
import io.kotest.property.arbitrary.alphanumeric
import io.kotest.property.arbitrary.arbitrary
import io.kotest.property.arbitrary.boolean
import io.kotest.property.arbitrary.int
import io.kotest.property.arbitrary.localDateTime
import io.kotest.property.arbitrary.string
import nl.uwv.smz.diamond.extern.impl.ExternConfig
import nl.uwv.smz.diamond.persistence.impl.DatabaseConfig

fun Arb.Companion.serverConfig() = arbitrary {
    KtorConfig(
        port = int(1000..9000).bind(),
    )
}

fun Arb.Companion.databaseConfig() = arbitrary {
    DatabaseConfig(
        stubEnabled = boolean().bind(),
        jdbcUrl = string().bind(),
        username = string().bind(),
        password = Masked(string().bind()),
    )
}

fun Arb.Companion.externConfig() =
    arbitrary {
        ExternConfig(
            postsServiceBaseUrl = string(1..10, codepoints = Codepoint.alphanumeric()).bind(),
    )
}

fun Arb.Companion.envConfig() = arbitrary {
    EnvConfig(
        ktor = serverConfig().bind(),
        database = databaseConfig().bind(),
        extern = externConfig().bind(),
    )
}

fun Arb.Companion.buildProperties() = arbitrary {
    BuildProperties(
        appVersion = int(1..100).bind().toString(),
        buildTime = localDateTime().bind(),
    )
}

fun Arb.Companion.globalConfig() = arbitrary {
    GlobalConfig(
        envConfig().bind(),
        buildProperties().bind(),
    )
}
