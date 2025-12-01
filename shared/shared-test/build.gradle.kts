plugins {
    id("diamond-kotlin-common")
    // NO id("diamond-kotlin-test") ... would be cyclic dependency!
}

dependencies {
    api(Deps.testing.kotest.property)
    implementation(Deps.testing.kotest.frameworkEngine)
    implementation(project(":shared:shared-common"))
    implementation(project(":shared:shared-logging"))
}
