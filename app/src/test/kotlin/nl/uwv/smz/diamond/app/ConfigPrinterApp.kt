package nl.uwv.smz.diamond.app

import nl.uwv.smz.diamond.shared.config.configPrinterAsAsciidoc
import nl.uwv.smz.diamond.shared.config.parseConfigEntries
import java.io.File

// TODO execute java class from gradle build; in CI, execute before creating asciidoc PDF
object ConfigPrinterApp {

    private val targetFolder = File("doc/SoftwareArchitectureDocument/src/docs/asciidoc/includes")
    private val target = File(targetFolder, "config_report.generated.adoc")

    @JvmStatic
    fun main(args: Array<String>) {
        check(targetFolder.exists()) { "Target folder ${targetFolder.absolutePath} does not exist!" }

        val report = configPrinterAsAsciidoc(parseConfigEntries(Config::class))
        println(report)
        println()
        target.writeText(report)
        println("Saved config report to: ${target.absolutePath}")
    }
}
