plugins {
    id("diamond-kotlin-common")
}

dependencies {
    implementation(project(":view:controller-api"))
    implementation(project(":shared:shared-common"))
    implementation(project(":domain:domain-logic-api"))

    implementation(Deps.koin.core)
}
