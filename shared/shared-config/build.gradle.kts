plugins {
    id("diamond-kotlin-common")
    id("diamond-kotlin-test")
}

dependencies {
    api(Deps.config.core)
    implementation(Deps.logging.kotlin)
    implementation(kotlin("reflect"))
    api(Deps.arrowCore)

//    testRuntimeOnly(Deps.config.yaml)
}
