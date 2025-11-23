plugins {
    id("diamond-kotlin-common")
    id("diamond-kotlin-test")
}

dependencies {
    implementation(project(":client-sdk"))
    implementation(project(":app"))
    implementation(Deps.logging.kotlin)
}
