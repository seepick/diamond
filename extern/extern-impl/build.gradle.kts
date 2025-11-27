plugins {
    id("diamond-kotlin-common")
    id("diamond-kotlin-test")
    id("org.openapi.generator")
}

dependencies {
    implementation(project(":extern:extern-generated"))
    testImplementation(project(":shared:shared-wiremock"))
}
