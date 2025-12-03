package nl.uwv.smz.diamond.openapigen

import org.openapitools.codegen.CodegenConstants
import org.openapitools.codegen.DefaultGenerator
import org.openapitools.codegen.config.CodegenConfigurator
import java.io.File

data class Generation(
    val name: String,
    val targetGenFolder: String,
    val packageApi: String = "testgen.api",
    val packageModel: String = "testgen.model",
    val pathToYml: String = "test-simple.yml",
)

fun runGenerator(gen: Generation) {
    val configurator = CodegenConfigurator()
        .setGeneratorName(gen.name)
        .setInputSpec(gen.pathToYml)
        .setAdditionalProperties(
            mapOf(
                CodegenConstants.API_PACKAGE to gen.packageApi,
                CodegenConstants.MODEL_PACKAGE to gen.packageModel,
            ),
        )
        .setOutputDir(gen.targetGenFolder)

    DefaultGenerator().opts(configurator.toClientOptInput()).generate()
}

fun assertFilesExisting(targetGenFolder: String, files: List<String>) {
    files.map { File("$targetGenFolder/src/main/kotlin/$it") }
        .filter { !it.exists() }
        .ifNotEmpty {
            throw AssertionError(
                "Missing files ${it.joinToString("\n") { "- ${it.absolutePath}" }}",
            )
        }
}

inline fun <T> List<T>.ifNotEmpty(code: (List<T>) -> Unit) {
    if (isNotEmpty()) {
        code(this)
    }
}
