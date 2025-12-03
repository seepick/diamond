package nl.uwz.smz.diamond.openapigen

import org.openapitools.codegen.CodegenType

// register me in src/main/resources/META-INF/services/org.openapitools.codegen.CodegenConfig
class DiamondModelGenerator : DiamondAbstractGenerator(
    generatorName = "diamond-model",
    help = "Generates OpenAPI Kotlin data class with kotlinx-serialization support.",
    type = CodegenType.SCHEMA,
) {
    init {
        apiTemplateFiles.clear()
    }
}
