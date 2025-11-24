@Suppress("MayBeConstant", "unused", "ClassName")
object Versions {
    val java = 17
    val kotlin = "2.2.21"
    val koin = "4.1.1"
    val ktor = "3.3.2"
    val h2 = "2.3.232"
    val exposed = "0.61.0"

    object Logging {
        val kotlin = "7.0.13"
        val logback = "1.5.18"
    }

    object testing {
        val junit = "6.0.1" // // 5.8.2
        val kotest = "5.9.1"
    }
}

object Plugins {
    val manesVersions = "0.45.0"
}
/*
[bundles]
#ktor = ["ktor-core", "ktor-json", "ktor-foobar"]

[plugins]
# short-notation = "some.plugin.id:1.4"
#versions = { id = "com.github.ben-manes.versions", version.ref = "manes-versions" }

 */
