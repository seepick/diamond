plugins {
    id("diamond-kotlin-common")
    id("diamond-kotlin-test")
}

dependencies {
    api(project(":extern:extern-api"))
    api(project(":domain:domain-failure"))
    implementation(project(":extern:extern-generated"))
    implementation(project(":shared:shared-common"))
    implementation(project(":shared:shared-config"))
    implementation(Deps.jsch)
    implementation(Deps.koin.core)
    implementation(Deps.logging.kotlin)

    testImplementation(project(":shared:shared-wiremock"))
    testImplementation(Deps.testing.testcontainers.main)
}
