plugins {
    id("diamond-kotlin-common")
    id("diamond-kotlin-test")
}

dependencies {
    api(project(":view:controller-api"))
    implementation(project(":domain:domain-logic-api"))
    implementation(project(":shared:shared-common"))

    implementation(Deps.koin.core)
}
