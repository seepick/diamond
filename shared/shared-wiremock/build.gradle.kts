plugins {
    id("diamond-kotlin-common")
}

dependencies {
    implementation(Deps.testing.kotest.frameworkEngine)
    api(Deps.testing.wiremock)
}
