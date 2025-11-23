plugins {
    id("diamond-kotlin-common")
}

dependencies {
    api(project(":domain:domain-failure"))
    implementation(project(":shared:shared-common"))
    api(Deps.arrowCore)
}
