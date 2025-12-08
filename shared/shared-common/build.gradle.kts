plugins {
    id("diamond-kotlin-common")
    id("diamond-kotlin-test")
    id("java-test-fixtures")
}

dependencies {
    api(Deps.arrowCore)
    implementation(Deps.logging.kotlin)
}
