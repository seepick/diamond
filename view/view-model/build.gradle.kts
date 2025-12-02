plugins {
    id("diamond-kotlin-common")
    kotlin("plugin.serialization")

    id("diamond-kotlin-test")
    id("java-test-fixtures")
}

dependencies {
    api(project(":domain:domain-failure"))
    implementation(Deps.serializationx)

    testImplementation(Deps.testing.jsonAssert)
    testFixturesApi(project(":shared:shared-test"))
    testFixturesApi(Deps.testing.kotest.property)
}
