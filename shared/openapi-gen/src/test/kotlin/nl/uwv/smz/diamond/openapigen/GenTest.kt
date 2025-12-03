package nl.uwv.smz.diamond.openapigen

import io.kotest.assertions.withClue
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.booleans.shouldBeTrue
import org.openapitools.codegen.CodegenConstants
import org.openapitools.codegen.DefaultGenerator
import org.openapitools.codegen.config.CodegenConfigurator
import java.io.File

class GenTest : StringSpec({

    val targetGenFolder = "build/testGenFolder"

    "generate" {
        val configurator = CodegenConfigurator()
            .setGeneratorName("diamond-server")
            .setInputSpec("test-simple.yml")
            .setAdditionalProperties(
                mapOf(
                    CodegenConstants.API_PACKAGE to "testgen.api",
                    CodegenConstants.MODEL_PACKAGE to "testgen.model",
                ),
            )
            .setOutputDir(targetGenFolder)

        DefaultGenerator().opts(configurator.toClientOptInput()).generate()

        listOf("testgen/api/DogsApi.kt", "testgen/model/Dog.kt").map { filePath ->
            val generatedFile = File("$targetGenFolder/src/main/kotlin/$filePath")
            withClue("Expected [${generatedFile.absolutePath}] to be existing.") {
                generatedFile.exists().shouldBeTrue()
            }
        }
    }
})
