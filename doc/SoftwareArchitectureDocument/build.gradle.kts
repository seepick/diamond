import org.asciidoctor.gradle.jvm.AsciidoctorTask

// https://docs.asciidoctor.org/pdf-converter/latest/
// https://asciidoctor.github.io/asciidoctor-gradle-examples/

repositories {
    mavenCentral()
}

plugins {
    val asciidoctorVersion = "4.0.5"
//    java
    id("diamond-versions")
    id("org.asciidoctor.jvm.convert") version asciidoctorVersion
    id("org.asciidoctor.jvm.pdf") version asciidoctorVersion
    id("org.asciidoctor.jvm.gems") version asciidoctorVersion // IMPORTANT! without it we get weird JRuby errors!
//    id("org.asciidoctor.jvm.diagram") version asciidoctorVersion // .. not found?!
//    id("com.github.jruby-gradle.base") version "1.3.0" // to add gems in dependencies
    // id("io.freefair.plantuml") version "6.6.3"
}

dependencies {
    // asciidoctorGems 'rubygems:rouge:3.15.0'
//    implementation("org.yaml:snakeyaml:2.5")
//    gems("rubygems:asciidoctor-diagram:1.4.0")
    // https://docs.asciidoctor.org/diagram-extension/latest/diagram_types/plantuml/
    // gem("asciidoctor-diagram-plantuml:1.2025.3")
}
// nope ... https://docs-as-co.de/news/plantuml-gradle/

// https://asciidoctor.github.io/asciidoctor-gradle-plugin/master/user-guide/

tasks.withType<AsciidoctorTask>().configureEach {
    attributes(mapOf("foo" to "now1"))
}

tasks.asciidoctorPdf {
    attributes(mapOf("foo" to "now2")) // THIS works!
}
// tasks.named("build") {
//    dependsOn(tasks.named<AsciidoctorTask>("asciidoctorPdf"))
// }

// Convenience task to open the generated PDF (macOS example)
// tasks.register<Exec>("openPdf") {
//     dependsOn("asciidoctor")
//     commandLine("open", layout.buildDirectory.file("asciidoc/pdf/my-awesome-manual.pdf").get().asFile)
// }

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
            attribute("foo", "gradle1")
        }
        baseDirFollowsSourceDir()
//        javaToolchains {
//        }

//        outputDir = file("$buildDir/docs")
//        sources(delegateClosureOf<PatternSet> {
//            include("toplevel.adoc", "another.adoc", "third.adoc")
//        })
        // asciidoctor-diagram-plantuml
        options(
            mapOf(
                "doctype" to "book",
                "ruby" to "erubis",
                "foo" to "fromOptions",
                "attributes" to mapOf("foo" to "fromInternalOpts"),
            )
        )
        doFirst {
            attributes = mapOf("foo" to "gradle3")
        }
        // THIS should be actually the way to go...?!
        attributes(
            mapOf(
                "foo" to "gradle2",
//                "basedir" to "src/docs/asciidoc",
//                "sourcedir" to "src/docs/asciidoc/",
//                "imagesdir" to "src/docs/asciidoc/images/",
                "source-highlighter" to "coderay",
                "toc" to "left",
                "idprefix" to "",
                "idseparator" to "-",
            )
        )
    }
}
// asciidoctorPdf {
//    dependsOn asciidoctorGemsPrepare

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
