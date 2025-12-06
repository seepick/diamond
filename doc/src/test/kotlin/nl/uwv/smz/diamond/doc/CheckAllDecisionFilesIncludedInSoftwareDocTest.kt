package nl.uwv.smz.diamond.doc

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldBeEmpty
import java.io.File

class CheckAllDecisionFilesIncludedInSoftwareDocTest : StringSpec({

    val adrRoot = File("decisions").apply { require(exists()) }
    val sdocRoot = File("SoftwareDocument/src/docs/asciidoc").apply { require(exists()) }

    fun File.scanRecursively(): List<File> =
        listFiles { it.extension == "adoc" && it.name != "README.adoc" }.toList() +
            listFiles { it.isDirectory }.map { it.scanRecursively() }.flatten()

    "When scan all files Then all decision files are included at least once" {
        val leftovers = adrRoot.scanRecursively().map { it.absolutePath.substringAfter("/decisions/") }.toMutableSet()
        val referenced = sdocRoot.scanRecursively().map { file ->
            file.readLines()
                .filter { line ->
                    line.startsWith("include::{adrsdir}") &&
                        line.endsWith("[leveloffset=+2]")
                }
                .map {
                    it.substringAfter("include::{adrsdir}/")
                        .substringBefore("[leveloffset=+2]").trim()
                }
        }.flatten().toSet()

        (leftovers - referenced).shouldBeEmpty()
    }
})
