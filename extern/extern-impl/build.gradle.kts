plugins {
    id("diamond-kotlin-common")
    id("diamond-kotlin-test")
    id("org.openapi.generator")
}

dependencies {
    implementation(project(":extern:extern-generated"))
    testImplementation("org.wiremock:wiremock:3.13.2")
}
