plugins {
    id("diamond-kotlin-common")
}

dependencies {
    api(project(":persistence:persistence-api"))
    api(project(":shared:shared-common"))
    implementation(Deps.koin.core)
}
