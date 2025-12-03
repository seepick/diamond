package nl.uwv.smz.diamond.openapigen

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.booleans.shouldBeFalse
import java.io.File

class DiamondModelGeneratorITest : StringSpec({
    "generate" {
        val targetGenFolder = "build/testgenModel"
        val gen = Generation(
            name = "diamond-model",
            targetGenFolder = targetGenFolder,
            packageApi = "testgen.api",
            packageModel = "testgen.model",
        )

        runGenerator(gen)

        assertFilesExisting(targetGenFolder, listOf("testgen/model/Dog.kt"))
        File("$targetGenFolder/testgen/api").exists().shouldBeFalse()
    }
})
