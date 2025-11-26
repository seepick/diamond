plugins {
    id("diamond-kotlin-common")
    id("diamond-kotlin-test")
}

dependencies {
    api(Deps.hoplite.core)
    api(Deps.arrowCore)
    implementation(Deps.logging.kotlin)
}
