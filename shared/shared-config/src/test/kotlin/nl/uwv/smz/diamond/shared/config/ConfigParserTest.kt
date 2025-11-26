package nl.uwv.smz.diamond.shared.config

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldBeSingleton
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.nulls.shouldNotBeNull

// TODO how to handel config set from outside, vs config always fixed inside...?

class ConfigParserTest : StringSpec({
    // TODO could test that every declared property must have one or the other annotation in a config class
    "read simple" {
        data class SimpleConfig(
            @ConfigProperty("description")
            val property: String,
        )
        parseConfigEntries(SimpleConfig::class) shouldBeEqual listOf(
            ConfigEntry(listOf("property"), ConfigType.String, "description"),
        )
    }
    "read with default" {
        data class DefaultedConfig(
            @ConfigProperty("description")
            val property: String = "defaultValue",
        )
        parseConfigEntries(DefaultedConfig::class).shouldBeSingleton().first()
            .default.shouldNotBeNull() shouldBeEqual "defaultValue"
    }
    "read simple super with sub" {
        data class TestSubConfig(
            @ConfigProperty("some sub description")
            val subProperty: String,
        )

        data class TestSuperConfig(
            @SubConfig
            val subConfig: TestSubConfig,
        )

        parseConfigEntries(TestSuperConfig::class) shouldBeEqual listOf(
            ConfigEntry(listOf("subConfig", "subProperty"), ConfigType.String, "some sub description"),
        )
    }
    "read super and sub has property" {
        data class TestSubConfig(
            @ConfigProperty("some sub description")
            val subProperty: String,
        )

        data class TestSuperConfig(
            @SubConfig
            val subConfig: TestSubConfig,
            @ConfigProperty("some super description")
            val superProperty: Int,
        )

        parseConfigEntries(TestSuperConfig::class) shouldBeEqual listOf(
            ConfigEntry(listOf("superProperty"), ConfigType.Integer, "some super description"),
            ConfigEntry(listOf("subConfig", "subProperty"), ConfigType.String, "some sub description"),
        )
    }
})
