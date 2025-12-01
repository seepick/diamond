// https://docs.asciidoctor.org/pdf-converter/latest/
// https://asciidoctor.github.io/asciidoctor-gradle-examples/

repositories {
    mavenCentral()
}

plugins {
    val asciidoctorVersion = "4.0.5"
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

tasks.asciidoctorPdf {
    asciidoctorj {
        // AsciidoctorJExtension
        // The AsciidoctorJ engine supports Batik, Ditaa, JSyntrax, and PlantUml via a Diagram extension
        modules {
//                diagram.use()
//                diagram.version("1.5.16")
//                pdf.setVersion("1.2.3")
//                diagram
        }
    }
    options(
        mapOf(
            "doctype" to "book",
            "ruby" to "erubis",
        ),
    )
    // THIS should be actually the way to go...?!
    attributes(
        mapOf(
            "basedir" to "src/docs/asciidoc",
            "sourcedir" to "src/main/kotlin",
            "source-highlighter" to "coderay",
            "toc" to "left",
            "idprefix" to "",
            "idseparator" to "-",
        ),
    )
}

// asciidoctorPdf {
//    dependsOn asciidoctorGemsPrepare

// tasks.named("build") {
//    dependsOn(tasks.named<AsciidoctorTask>("asciidoctorPdf"))
// }

// Convenience task to open the generated PDF (macOS example)
// tasks.register<Exec>("openPdf") {
//     dependsOn("asciidoctor")
//     commandLine("open", layout.buildDirectory.file("asciidoc/pdf/xxx.pdf").get().asFile)
// }
