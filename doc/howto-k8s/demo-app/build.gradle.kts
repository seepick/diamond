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
    implementation("ch.qos.logback:logback-classic:1.5.22")
}

application {
    mainClass.set("demoApp.DemoApp")
}
