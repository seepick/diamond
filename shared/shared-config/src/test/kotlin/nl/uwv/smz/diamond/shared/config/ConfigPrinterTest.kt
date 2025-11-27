package nl.uwv.smz.diamond.shared.config

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.equals.shouldBeEqual

class ConfigPrinterTest : StringSpec({
    "complete test" {
        configPrinterAsAsciidoc(
            listOf(
                // TODO test for path being: "foo_bar" or "fooBar"
                ConfigEntry(path = listOf("simple"), ConfigType.String, "description"),
                ConfigEntry(path = listOf("foo", "bar"), ConfigType.Integer, "description", "42")
            )
        ) shouldBeEqual """
            |===
            |Param |Type |Default |Description
            
            |`FOO_BAR`
            |integer
            |42
            |description

            |`SIMPLE`
            |string
            |-
            |description

            |===
            
            """.trimIndent()
    }
    // TODO more tests
})
