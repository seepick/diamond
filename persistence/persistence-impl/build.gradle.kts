plugins {
    id("diamond-kotlin-common")
    id("diamond-kotlin-test")
}

dependencies {
    api(project(":persistence:persistence-api"))
    implementation(project(":shared:shared-common"))
    implementation(project(":shared:shared-config"))
    implementation(Deps.koin.core)
    implementation(Deps.logging.kotlin)

    implementation("org.jetbrains.exposed:exposed-core:${Versions.exposed}")
    implementation("org.jetbrains.exposed:exposed-dao:${Versions.exposed}")
    implementation("org.jetbrains.exposed:exposed-jdbc:${Versions.exposed}")
    // TODO runtime optional!; default = oracle, dev/test = H2
    runtimeOnly("com.h2database:h2:${Versions.h2}")

    testImplementation(testFixtures(project(":domain:domain-model")))
}
