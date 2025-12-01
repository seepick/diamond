plugins {
    id("diamond-kotlin-common")
    id("diamond-kotlin-test")
}

dependencies {
    api(project(":persistence:persistence-api"))
    api(Deps.datetimex)
    implementation(project(":shared:shared-common"))
    implementation(project(":shared:shared-config"))
    api(Deps.hoplite.core) // due to DatabaseConfig.password: Masked
    implementation(Deps.koin.core)
    implementation(Deps.logging.kotlin)

    implementation(Deps.database.exposed.core)
    implementation(Deps.database.exposed.dao)
    implementation(Deps.database.exposed.jdbc)
    implementation(Deps.database.exposed.datetimex)
    implementation(Deps.database.liquibase.core)
    implementation(Deps.database.liquibase.slf4j)
    // TODO runtime optional!; default = oracle, dev/test = H2
    runtimeOnly(Deps.database.oracle)
    runtimeOnly(Deps.database.h2)

    testImplementation(testFixtures(project(":domain:domain-model")))
    testImplementation(testFixtures(project(":shared:shared-common")))
    testImplementation(Deps.testing.testcontainers.main)
    testImplementation(Deps.testing.testcontainers.oracle)
}

// $ ./gradlew test -PrunTestcontainersTests=true
val test by tasks.getting(Test::class) {
    // FIXME actually want to run additive: if tag set, then additionally! run those tests
    // https://kotest.io/docs/framework/tags.html
    val tagProperty = if (GradleProperty.testcontainers.isSet()) {
        gradleLog("running testcontainers tests")
        "kotest.tags" to Constants.kotestTestcontainersTag
    } else {
        "kotest.tags" to "!${Constants.kotestTestcontainersTag}"
    }
    systemProperties = enhanceSystemProperties(tagProperty)
}
