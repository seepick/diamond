package nl.uwv.smz.diamond.shared.config

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.equals.shouldBeEqual

data class TestConfig(
    @SubConfig
    val subConfig: TestSubConfig,
    @ConfigProperty("some description")
    val intProperty: Int,
)

data class TestSubConfig(
    @ConfigProperty("some sub description")
    val subStringProperty: String,
)

class ConfigParserTest : StringSpec({
    "read entries" {
        parseConfigEntries(TestConfig::class) shouldBeEqual listOf(
            ConfigEntry(listOf("intProperty"), "Int", "some description"),
            ConfigEntry(listOf("subConfig", "subStringProperty"), "String", "some sub description"),
        )
    }
})
