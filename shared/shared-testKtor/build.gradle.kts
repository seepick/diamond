plugins {
    id("diamond-kotlin-common")
    // NO id("diamond-kotlin-test") ... would be cyclic dependency!
}

dependencies {
    implementation(Deps.ktor.server.core)
    implementation(Deps.ktor.client.core)
    implementation(Deps.testing.kotest.frameworkEngine)
    implementation(project(":shared:shared-common"))
    implementation(project(":shared:shared-logging"))
}
