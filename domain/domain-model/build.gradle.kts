plugins {
    id("diamond-kotlin-common")
    id("java-test-fixtures")
}

dependencies {
    api(project(":domain:domain-failure"))
    implementation(project(":shared:shared-common"))
    api(Deps.arrowCore)

    testFixturesApi(Deps.testing.kotest.property)
    testFixturesImplementation(Deps.testing.kotest.assertionsArrow)
}
