plugins {
    id("diamond-kotlin-common")
}

dependencies {
    api(project(":extern:extern-api"))
    api(project(":shared:shared-common"))
    api(Deps.koin.core)
    implementation(Deps.logging.kotlin)
}
