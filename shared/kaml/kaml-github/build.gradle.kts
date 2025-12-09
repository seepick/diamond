plugins {
    id("diamond-kotlin-common")
    id("diamond-kotlin-test")
}

dependencies {
    implementation(project(":shared:kaml:kaml-core"))
    implementation("com.amihaiemil.web:eo-yaml:8.0.6")
}
