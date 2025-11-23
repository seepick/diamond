plugins {
    id("diamond-kotlin-common")
}

dependencies {
    implementation(project(":domain:domain-failure"))
    implementation(project(":shared:shared-common"))
    implementation(Deps.arrowCore)
}
