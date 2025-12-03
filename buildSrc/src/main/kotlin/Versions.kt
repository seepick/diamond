@Suppress("MayBeConstant", "unused", "ClassName", "ConstPropertyName")
object Versions {
    const val java = 17
    val arrow = "2.2.0"
    val kotlin = "2.2.21"
    val koin = "4.1.1"
    val ktor = "3.3.2"
    val h2 = "2.3.232"
    val exposed = "0.61.0"
    val hoplite = "3.0.0.RC1" // "2.9.0"
    val testcontainers = "2.0.2"
    val jsch = "2.27.7"
    val openapi = "7.17.0" // plugin and dependency

    object logging {
        val kotlin = "7.0.13"
        val logback = "1.5.18"
    }

    object testing {
        val junit = "6.0.1"
        val kotest = "6.0.5"
        val cucumber = "7.32.0"
    }
}
