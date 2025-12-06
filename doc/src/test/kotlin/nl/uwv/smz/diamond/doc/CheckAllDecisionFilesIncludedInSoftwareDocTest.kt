package nl.uwv.smz.diamond.doc

import io.kotest.assertions.withClue
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldBeEmpty
import java.io.File

class CheckAllDecisionFilesIncludedInSoftwareDocTest : StringSpec({

    val adrRoot = File("decisions").apply { require(exists()) }
    val sdocRoot = File("SoftwareDocument/src/docs/asciidoc").apply { require(exists()) }

    fun File.scanRecursively(): List<File> =
        listFiles { it.extension == "adoc" && it.name != "README.adoc" }.toList() +
            listFiles { it.isDirectory }.map { it.scanRecursively() }.flatten()

    "When scan all ADR files Then of them are included at least once in some ASCIIDOC file" {
        val leftovers = adrRoot.scanRecursively().map { it.absolutePath.substringAfter("/decisions/") }.toMutableSet()
        val referenced = sdocRoot.scanRecursively().map { file ->
            file.readLines()
                .filter { line ->
                    line.startsWith("include::{adrsdir}")
                }
                .map {
                    it.substringAfter("include::{adrsdir}/")
                        .substringBefore("[")
                }
        }.flatten().toSet()

        var decisionsWithoutBeingIncluded = (leftovers - referenced).toList().sorted()
        withClue(clue = {
            "${decisionsWithoutBeingIncluded.size} ADR files found without being included in the SoftwareDoc:\n" +
                decisionsWithoutBeingIncluded.joinToString("\n") { "  - $it" }
        }) {
            decisionsWithoutBeingIncluded.shouldBeEmpty()
        }
    }
})
