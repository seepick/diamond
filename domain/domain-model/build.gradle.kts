plugins {
    id("diamond-kotlin-common")
    id("java-test-fixtures")
}

dependencies {
    api(Deps.arrowCore)
    api(project(":domain:domain-failure"))
    implementation(project(":shared:shared-common"))

    testFixturesApi(Deps.testing.kotest.property)
    testFixturesImplementation(Deps.testing.kotest.assertionsArrow)
}
