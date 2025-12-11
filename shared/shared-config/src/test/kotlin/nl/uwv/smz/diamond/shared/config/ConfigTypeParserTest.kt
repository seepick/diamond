package nl.uwv.smz.diamond.shared.config

import com.sksamuel.hoplite.Secret
import com.sksamuel.hoplite.simpleName
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.equals.shouldBeEqual

class ConfigTypeParserTest : StringSpec({
    class SupportedTypes(
        val string: String,
        val int: Int,
        val bool: Boolean,
        val secret: Secret,
    )
    listOf(
        SupportedTypes::string to ConfigType.String,
        SupportedTypes::int to ConfigType.Integer,
        SupportedTypes::bool to ConfigType.Boolean,
        SupportedTypes::secret to ConfigType.String,
    ).forEach { (prop, type) ->
        // TODO parametrized test data all
        "${prop.returnType.simpleName} should match ${type.name}" {
            prop.returnType.toConfigType() shouldBeEqual type
        }
    }
})
