object Deps {

    object logging {
        val kotlin = "io.github.oshai:kotlin-logging-jvm:${Versions.Logging.kotlin}"
        val logback = "ch.qos.logback:logback-classic:${Versions.Logging.logback}"
    }

    object ktor {
        private fun make(artifact: String) = "io.ktor:ktor-$artifact:${Versions.ktor}"
        val serialization = make("serialization-kotlinx-json")

        object server {
            val testHost = make("server-test-host")
            val core = make("server-core")
            val netty = make("server-netty")
            val contentNegotiation = make("server-content-negotiation")
            val hostCommon = make("server-host-common")
            val statusPages = make("server-status-pages")
            val openApi = make("server-openapi")
        }

        object client {
            val core = make("client-core")
            val cio = make("client-cio")
        }
    }

    object koin {
        private fun make(artifact: String) = "io.insert-koin:koin-$artifact:${Versions.koin}"
        val core = make("core")
        val ktor = make("ktor")
        val logger = make("logger-slf4j")
    }

    object testing {
        object kotest {
            private fun make(artifact: String) = "io.kotest:kotest-$artifact:${Versions.testing.kotest}"
            val junitRunner = make("runner-junit5-jvm")
            val assertions = make("assertions-core")
            val property = make("property")
        }
    }

    object pluginIds {
        val manesVersion = "com.github.ben-manes.versions"
    }
}
