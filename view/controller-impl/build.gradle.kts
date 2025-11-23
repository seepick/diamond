plugins {
    id("diamond-kotlin-common")
}

dependencies {
    api(project(":view:controller-api"))
    implementation(project(":domain:domain-logic-api"))
    implementation(project(":shared:shared-common"))

    implementation(Deps.koin.core)
}
