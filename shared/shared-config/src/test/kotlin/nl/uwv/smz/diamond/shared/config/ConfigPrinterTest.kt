package nl.uwv.smz.diamond.shared.config

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.equals.shouldBeEqual

class ConfigPrinterTest : StringSpec({
    "single entry" {
        configPrinterAsAsciidoc(
            listOf(
                // TODO test for path being: "foo_bar" or "fooBar"
                ConfigEntry(path = listOf("simple"), "string", "description"),
                ConfigEntry(path = listOf("foo", "bar"), "type", "description")
            )
        ) shouldBeEqual """
            |===
            |Param |Type |Description
            
            |`FOO_BAR`
            |type
            |description

            |`SIMPLE`
            |string
            |description

            |===
            
            """.trimIndent()
    }
    // TODO more tests
})
