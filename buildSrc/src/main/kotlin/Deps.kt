@Suppress("MayBeConstant", "unused", "ClassName")
object Deps {

    val serializationx = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0"

    val arrowCore = "io.arrow-kt:arrow-core:2.2.0"

    object database {
        val h2 = "com.h2database:h2:${Versions.h2}"
        val oracle = "com.oracle.database.jdbc:ojdbc17:23.26.0.0.0"

        object liquibase {
            val core = "org.liquibase:liquibase-core:4.31.1"
            val slf4j = "com.mattbertolini:liquibase-slf4j:5.1.0"
        }

        object exposed {
            private fun make(artifact: String) = "org.jetbrains.exposed:exposed-$artifact:${Versions.exposed}"
            val core = make("core")
            val dao = make("dao")
            val jdbc = make("jdbc")
        }
    }

    object logging {
        val kotlin = "io.github.oshai:kotlin-logging-jvm:${Versions.logging.kotlin}"
        val logback = "ch.qos.logback:logback-classic:${Versions.logging.logback}"
    }

    object hoplite {
        private fun make(artifact: String) = "com.sksamuel.hoplite:hoplite-$artifact:${Versions.hoplite}"
        val core = make("core")
//        val yaml = make("yaml") NO!
    }

    object ktor {
        private fun make(artifact: String) = "io.ktor:ktor-$artifact:${Versions.ktor}"
        val serialization = make("serialization-kotlinx-json")
        val io = make("io-jvm")

        object server {
            val testHost = make("server-test-host")
            val core = make("server-core")
            val netty = make("server-netty")
            val contentNegotiation = make("server-content-negotiation")
            val hostCommon = make("server-host-common")
            val statusPages = make("server-status-pages")
        }

        object client {
            val core = make("client-core")
            val cio = make("client-cio")
            val contentNegotiation = make("client-content-negotiation")
        }
    }

    object koin {
        private fun make(artifact: String) = "io.insert-koin:koin-$artifact:${Versions.koin}"
        val core = make("core")
        val ktor = make("ktor")
        val test = make("test")
        val logger = make("logger-slf4j")
    }

    object testing {
        val mockk = "io.mockk:mockk:1.14.6"
        val wiremock = "org.wiremock:wiremock:3.13.2"

        object junit {
            val platformSuite = "org.junit.platform:junit-platform-suite:${Versions.testing.junit}"
            val jupiter = "org.junit.jupiter:junit-jupiter:${Versions.testing.junit}"
            val jupiterApi = "org.junit.jupiter:junit-jupiter-api:${Versions.testing.junit}"
        }

        object kotest {
            private fun make(artifact: String) = "io.kotest:kotest-$artifact:${Versions.testing.kotest}"
            val frameworkEngine = make("framework-engine")
            val junitRunner = make("runner-junit5-jvm")
            val assertions = make("assertions-core")
            val property = make("property")
            /** https://kotest.io/docs/assertions/arrow.html */
            val assertionsArrow = "io.kotest.extensions:kotest-assertions-arrow:2.0.0" // different version
        }

        object testcontainers {
            private fun make(suffix: String) = "org.testcontainers:testcontainers${suffix}:${Versions.testcontainers}"
            val main = make("")
            val oracle = make("-oracle-free") // TODO or testcontainers-oracle-xe?
        }

        object cucumber {
            private fun make(artifact: String) = "io.cucumber:cucumber-$artifact:${Versions.testing.cucumber}"
            val java = make("java")
            val junitEngine = make("junit-platform-engine")
            val picocontainer = make("picocontainer")
        }
    }

    object pluginIds {
        val manesVersion = "com.github.ben-manes.versions"
    }
}
