// https://docs.asciidoctor.org/pdf-converter/latest/
// https://asciidoctor.github.io/asciidoctor-gradle-examples/

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

val asciidocSrcDir = file("src/docs/asciidoc")

gradleLog("AsciiDoc source dir: [${"${project.projectDir}/src/docs/asciidoc"}]")

fun asciidocAttributes(
    sourceHighlighter: String,
    imagesDir: String,
    more: Map<String, String> = emptyMap(),
): Map<String, String> = mapOf(
    "basedir" to asciidocSrcDir.absolutePath,
    "adrsdir" to "${project.projectDir}/../decisions",
    "imgdir" to imagesDir,
    "source-highlighter" to sourceHighlighter,
    "toc" to "left",
    "toclevels" to "3",
    "sectnums" to "",
    "sectnumlevels" to "4",
    "icons" to "font",
    "idprefix" to "",
    "idseparator" to "-",
) +
    more

// val asciidoctor by tasks.registering(AsciidoctorTask::class) {
tasks.asciidoctor {
//    group = "diamond asciidoc group name"
//    description = "diamond asciidoc group description"
    setSourceDir(asciidocSrcDir)
    backends().add("html5")
    sources {
        include("index.adoc") // otherwise treat included files as root-files
    }
    options(
        mapOf(
            "doctype" to "article",
        ),
    )
    attributes(
        asciidocAttributes(
            sourceHighlighter = "highlightjs",
            // Make generated HTML use a relative images path: <img src="images/diamond.jpg">
            imagesDir = "images",
        ),
    )
    resources {
        from(asciidocSrcDir.resolve("images")) {
            into("images")
        }
    }
}

tasks.asciidoctorPdf {
    setSourceDir(asciidocSrcDir)
    sources {
        include("index.adoc") // otherwise treat included files as root-files
    }
    options(
        mapOf(
            "doctype" to "book",
            "ruby" to "erubis",
        ),
    )
    attributes(
        asciidocAttributes(
            sourceHighlighter = "coderay",
            imagesDir = "${asciidocSrcDir.absolutePath}/images",
            more = mapOf(
                "isPdf" to "true",
                "pdf-theme" to "diamond-pdf",
                "pdf-themesdir" to "${project.projectDir}/src/docs/themes/",
//                "pdf-fontsdir" to "${project.projectDir}/src/docs/fonts",
            ),
        ),
    )
    jvm {
//             TODO JRuby accessing some internal JDK classes without proper module access causes warnings
// 2025-12-06T09:15:49.847+01:00 [WorkerExecutor Queue] WARN FilenoUtil :
// Native subprocess control requires open access to the JDK IO subsystem
// Pass '--add-opens java.base/sun.nio.ch=ALL-UNNAMED --add-opens java.base/java.io=ALL-UNNAMED' to enable.
//        jvmArgs.addAll(
//            listOf(
//                "--add-opens",
//                "java.base/sun.nio.ch=ALL-UNNAMED",
//                "--add-opens",
//                "java.base/java.io=ALL-UNNAMED",
//            ),
//        )
    }
    asciidoctorj {
        // TODO support emojis!
        // AsciidoctorJExtension
        // The AsciidoctorJ engine supports Batik, Ditaa, JSyntrax, and PlantUml via a Diagram extension
        modules {
//                diagram.use()
//                diagram.version("1.5.16")
//                pdf.setVersion("1.2.3")
//                diagram
        }
    }
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
