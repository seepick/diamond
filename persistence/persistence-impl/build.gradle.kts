plugins {
    id("diamond-kotlin-common")
    id("diamond-kotlin-test")
}

dependencies {
    api(project(":persistence:persistence-api"))
    implementation(Deps.koin.core)
    implementation(project(":shared:shared-common"))

    implementation("org.jetbrains.exposed:exposed-core:${Versions.exposed}")
    implementation("org.jetbrains.exposed:exposed-jdbc:${Versions.exposed}")
    runtimeOnly("com.h2database:h2:${Versions.h2}") // TODO runtime optional!; default = postgresql, dev/test = H2
}
