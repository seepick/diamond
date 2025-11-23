plugins {
    id("diamond-kotlin-common")
    kotlin("plugin.serialization")
}

dependencies {
    implementation(Deps.serializationx)
}
