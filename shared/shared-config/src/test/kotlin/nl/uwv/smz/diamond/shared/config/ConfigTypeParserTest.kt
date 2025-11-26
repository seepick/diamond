package nl.uwv.smz.diamond.shared.config

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.equals.shouldBeEqual

class ConfigTypeParserTest : StringSpec({
    class SupportedTypes(
        val string: String,
        val int: Int,
        val bool: Boolean,
    )

    listOf(
        SupportedTypes::string to ConfigType.String,
        SupportedTypes::int to ConfigType.Integer,
        SupportedTypes::bool to ConfigType.Boolean,
    ).forEach { (prop, type) ->
        prop.returnType.toConfigType() shouldBeEqual type
    }
})
