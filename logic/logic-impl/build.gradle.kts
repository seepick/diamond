plugins {
    id("diamond-kotlin-common")
}

dependencies {
    implementation(project(":logic:logic-api"))
    implementation(Deps.koin.core)
}
