plugins {
    id("diamond-kotlin-common")
    kotlin("plugin.serialization")
}

dependencies {
    api(project(":domain:domain-failure"))
    implementation(Deps.serializationx)
    implementation(Deps.datetimex)
}
