plugins {
    id("diamond-kotlin-common")
}

dependencies {
    implementation(project(":client-sdk"))
    implementation(project(":app"))
    implementation(Deps.logging.kotlin)

    testImplementation(Deps.testing.kotest.junitRunner)
    testImplementation(Deps.testing.kotest.assertions)
    // TODO kotest
}
