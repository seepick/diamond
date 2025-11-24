import org.asciidoctor.gradle.jvm.AsciidoctorTask

repositories {
    mavenCentral()
}

plugins {
    id("diamond-versions")
    id("org.asciidoctor.jvm.convert") version "4.0.5"
    id("org.asciidoctor.jvm.pdf") version "4.0.5"
//    id("org.asciidoctor.convert") version "1.5.12"
}

tasks {
    "asciidoctor"(AsciidoctorTask::class) {
//        sourceDir = file("docs")
//        outputDir = file("$buildDir/docs")
//        sources(delegateClosureOf<PatternSet> {
//            include("toplevel.adoc", "another.adoc", "third.adoc")
//        })
        options(mapOf("doctype" to "book", "ruby" to "erubis"))
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

//resources(delegateClosureOf<CopySpec> {
//  from("src/docs/asciidoc/images") {
//    include("**/*.png")
//    exclude("**/notThisOne.png")
//  }
//  from("$buildDir/downloads") {
//    include("deck.js/**")
//  }
//  into("./images")
//})
