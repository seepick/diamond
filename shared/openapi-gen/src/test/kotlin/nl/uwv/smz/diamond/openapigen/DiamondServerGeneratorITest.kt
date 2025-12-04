package nl.uwv.smz.diamond.openapigen

import io.kotest.core.spec.style.StringSpec

class DiamondServerGeneratorITest : StringSpec({
    "generate" {
        val targetGenFolder = "build/testgenServer"
        val gen = Generation(
            name = "diamond-server",
            targetGenFolder = targetGenFolder,
            packageApi = "testgen.api",
            packageModel = "testgen.model",
        )

        runGenerator(gen)

        assertSourceFilesExisting(
            targetGenFolder,
            "src/main/kotlin",
            "testgen/api/DogsApi.kt",
            "testgen/model/Dog.kt",
        )
    }
})
