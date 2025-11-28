import org.asciidoctor.gradle.jvm.AsciidoctorTask

repositories {
    mavenCentral()
}

plugins {
    id("diamond-versions")
    id("org.asciidoctor.jvm.convert") version "4.0.5"
    id("org.asciidoctor.jvm.pdf") version "4.0.5"
//    id("org.asciidoctor.jvm.diagram") version "4.0.5" // .. not found?!
//    id("com.github.jruby-gradle.base") version "1.3.0" // to add gems in dependencies
//    id("org.asciidoctor.convert") version "1.5.12"
}

dependencies {
//    gems("rubygems:asciidoctor-diagram:1.4.0")
    // https://docs.asciidoctor.org/diagram-extension/latest/diagram_types/plantuml/
    // gem("asciidoctor-diagram-plantuml:1.2025.3")
}
// nope ... https://docs-as-co.de/news/plantuml-gradle/

// https://asciidoctor.github.io/asciidoctor-gradle-plugin/master/user-guide/
tasks {
    "asciidoctor"(AsciidoctorTask::class) {
        asciidoctorj { // AsciidoctorJExtension
            // The AsciidoctorJ engine supports Batik, Ditaa, JSyntrax, and PlantUml via a Diagram extension
            modules {
//                diagram.use()
//                diagram.version("1.5.16")
//                pdf.setVersion("1.2.3")
//                diagram
            }
        }
//        javaToolchains {
//        }

        sourceDir(file("src/docs/asciidoc"))
//        outputDir = file("$buildDir/docs")
//        sources(delegateClosureOf<PatternSet> {
//            include("toplevel.adoc", "another.adoc", "third.adoc")
//        })
        // asciidoctor-diagram-plantuml
        options(
            mapOf(
                "doctype" to "book",
                "ruby" to "erubis",
            )
        )
        attributes(
            mapOf(
                "source-highlighter" to "coderay",
                "toc" to "",
                "idprefix" to "",
                "idseparator" to "-"
            )
        )
    }
}

// resources(delegateClosureOf<CopySpec> {
//  from("src/docs/asciidoc/images") {
//    include("**/*.png")
//    exclude("**/notThisOne.png")
//  }
//  from("$buildDir/downloads") {
//    include("deck.js/**")
//  }
//  into("./images")
// })
