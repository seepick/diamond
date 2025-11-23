plugins {
    id("diamond-kotlin-common")
}

dependencies {
    implementation(project(":domain:domain-model"))
    implementation(project(":domain:domain-failure"))
    implementation(Deps.arrowCore)
}
