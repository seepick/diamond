plugins {
    id("diamond-kotlin-common")
}

dependencies {
    api(project(":persistence:persistence-api"))
    implementation(Deps.koin.core)

    // DB
//    implementation("org.jetbrains.exposed:exposed-core:${Versions.exposed}")
//    implementation("org.jetbrains.exposed:exposed-jdbc:${Versions.exposed}")
//    implementation("com.h2database:h2:${Versions.h2}") // TODO runtime optional; default = postgresql, dev/test = H2
}
