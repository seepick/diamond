plugins {
    id("diamond-kotlin-common")
    id("diamond-kotlin-test")
}

dependencies {
    implementation(Deps.logging.kotlin)
    implementation("org.openapitools:openapi-generator:7.17.0")
}
