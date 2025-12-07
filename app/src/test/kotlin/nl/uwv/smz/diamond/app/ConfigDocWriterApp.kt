package nl.uwv.smz.diamond.app

import nl.uwv.smz.diamond.shared.config.configPrinterAsAsciidoc
import nl.uwv.smz.diamond.shared.config.parseConfigEntries
import java.io.File

object ConfigDocWriterApp {

    // TODO make configurable from outside
    private val targetFolder = File("doc/SoftwareDocument/src/docs/asciidoc/generated/")
    private val target = File(targetFolder, "environment_variables.generated.adoc")

    @JvmStatic
    fun main(args: Array<String>) {
        println("Going to generate the documentation part for the application configuration ...")
        check(targetFolder.exists()) { "Target folder ${targetFolder.absolutePath} does not exist!" }

        val report = configPrinterAsAsciidoc(parseConfigEntries(EnvConfig::class))
        target.writeText(report)
        println("Successfully saved config report to: ${target.absolutePath} ✅")
    }
}
