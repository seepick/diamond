plugins {
    id("diamond-kotlin-common")
    id("java-test-fixtures")
}

dependencies {
    api(Deps.datetimex)
    api(Deps.arrowCore)
    implementation(Deps.logging.kotlin)
}
