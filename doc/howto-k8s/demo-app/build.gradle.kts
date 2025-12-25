plugins {
    kotlin("jvm") version "2.3.0"
    id("io.ktor.plugin") version "3.3.3"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core:3.3.3")
    implementation("io.ktor:ktor-server-netty:3.3.3")
    implementation("io.github.oshai:kotlin-logging-jvm:7.0.13")
    runtimeOnly("ch.qos.logback:logback-classic:1.5.22")

    implementation("org.jetbrains.exposed:exposed-core:0.61.0")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.61.0")

    implementation("org.liquibase:liquibase-core:4.31.1")
    runtimeOnly("com.mattbertolini:liquibase-slf4j:5.1.0")
    runtimeOnly("org.postgresql:postgresql:42.1.4")
}

application {
    mainClass.set("demoApp.DemoApp")
}
