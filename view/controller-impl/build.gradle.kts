plugins {
    id("diamond-kotlin-common")
}

dependencies {
    implementation(project(":view:controller-api"))
    implementation(project(":view:view-model"))
    implementation(project(":shared:shared-common"))
    implementation(project(":domain:domain-logic-api"))
    implementation(project(":domain:domain-model"))

    implementation(Deps.koin.core)
}
