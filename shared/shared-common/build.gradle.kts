plugins {
    id("diamond-kotlin-common")
    id("java-test-fixtures")
}

dependencies {
    api(Deps.arrowCore)
    implementation(Deps.logging.kotlin)
}
