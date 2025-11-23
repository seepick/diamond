plugins {
    id("diamond-kotlin-common")
}

dependencies {
    implementation(project(":domain:domain-logic-api"))
    implementation(project(":shared:shared-common"))
    implementation(Deps.koin.core)
}
