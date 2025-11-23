plugins {
    id("diamond-kotlin-common")
}

dependencies {
    implementation(project(":domain:domain-logic-api"))
    implementation(project(":domain:domain-model"))
    implementation(project(":domain:domain-failure"))
    implementation(project(":shared:shared-common"))
    implementation(Deps.koin.core)
    implementation(Deps.logging.kotlin)
    implementation(Deps.arrowCore)

}
