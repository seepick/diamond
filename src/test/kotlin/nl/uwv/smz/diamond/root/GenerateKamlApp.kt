package nl.uwv.smz.diamond.root

import com.github.seepick.kaml.github.yaml.toYamlString
import java.io.File

object GenerateKamlApp {
    private const val GITHUB_WORKFLOWS_PATH = ".github/workflows"

    @JvmStatic
    fun main(args: Array<String>) {
        println("Received configured baseDir: ${args[0]}")
        val baseDir = File(args[0])
        require(baseDir.isDirectory)
        val githubFolder = File(baseDir, GITHUB_WORKFLOWS_PATH)
        githubFolder.mkdirs()

        listOf(githubContinuous to "continuous.gen.yml").forEach { (yaml, target) ->
            val target = File(githubFolder, target)
            target.writeText(yaml.toYamlString())
            println("Written YAML to: ${target.absolutePath}")
        }
    }
}
