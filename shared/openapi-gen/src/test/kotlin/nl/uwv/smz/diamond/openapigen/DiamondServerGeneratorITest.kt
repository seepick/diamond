package nl.uwv.smz.diamond.openapigen

import io.kotest.core.spec.style.StringSpec

class DiamondServerGeneratorITest : StringSpec({
    "Given dogs-API When generate Then files existing" {
        val targetGenFolder = "build/testgenServer"
        val gen = Generation(
            name = "diamond-server",
            targetGenFolder = targetGenFolder,
            packageApi = "testgen.api",
            packageModel = "testgen.model",
            pathToYml = "dogs-api.yml",
        )

        runGenerator(gen)

        assertSourceFilesExisting(
            targetGenFolder = targetGenFolder,
            sourceFolder = "src/main/kotlin",
            "testgen/api/DogsApi.kt",
            "testgen/model/Dog.kt",
        )
    }
})
