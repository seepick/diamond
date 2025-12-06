package nl.uwv.smz.diamond.openapigen

import io.kotest.core.spec.style.StringSpec
import java.io.File

class DiamondServerGeneratorITest : StringSpec({

    fun buildTargetGenFolder() = File.createTempFile("diamondTestOpenapi", "server").parentFile

    "Given dogs-API When generate Then files existing" {
        val targetFolder = buildTargetGenFolder()
        val gen = Generation(
            name = "diamond-server",
            targetGenFolder = targetFolder,
            packageApi = "testgen.api",
            packageModel = "testgen.model",
            pathToYml = "dogs-api.yml",
        )

        runGenerator(gen)

        assertSourceFilesExisting(
            targetGenFolder = targetFolder,
            sourceFolder = "src/main/kotlin",
            "testgen/api/DogsApi.kt",
            "testgen/model/Dog.kt",
        )
    }
})
