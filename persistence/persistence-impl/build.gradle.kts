plugins {
    id("diamond-kotlin-common")
    id("diamond-kotlin-test")
}

dependencies {
    api(project(":persistence:persistence-api"))
    implementation(project(":shared:shared-common"))
    implementation(project(":shared:shared-config"))
    api(Deps.hoplite.core) // due to DatabaseConfig.password: Masked
    implementation(Deps.koin.core)
    implementation(Deps.logging.kotlin)

    implementation("org.jetbrains.exposed:exposed-core:${Versions.exposed}")
    implementation("org.jetbrains.exposed:exposed-dao:${Versions.exposed}")
    implementation("org.jetbrains.exposed:exposed-jdbc:${Versions.exposed}")
    // TODO runtime optional!; default = oracle, dev/test = H2
    runtimeOnly("com.oracle.database.jdbc:ojdbc17:23.26.0.0.0")
    runtimeOnly("com.h2database:h2:${Versions.h2}")

    implementation("org.liquibase:liquibase-core:4.31.1")
    implementation("com.mattbertolini:liquibase-slf4j:5.1.0")

    testImplementation(testFixtures(project(":domain:domain-model")))
    val testcontainersVersion = "2.0.2"
    testImplementation("org.testcontainers:testcontainers:${testcontainersVersion}")
    testImplementation("org.testcontainers:testcontainers-oracle-free:${testcontainersVersion}") // or testcontainers-oracle-xe?
}

// $ ./gradlew test -PrunTestcontainerTests=true
val test by tasks.getting(Test::class) {
    val testcontainerTagName = "testcontainer" // see: nl.uwv.smz.diamond.shared.test.KoTags
    val runTestcontainerTests = providers.gradleProperty("runTestcontainerTests").isPresent
    systemProperties = System.getProperties().asIterable().associate { it.key.toString() to it.value }.let {
        if (runTestcontainerTests) {
            println("[DIAMOND-GRADLE:persistence:persistence-impl] running testcontainer tests")
            // https://kotest.io/docs/framework/tags.html
            it + ("kotest.tags" to testcontainerTagName)
        } else {
            it + ("kotest.tags" to "!$testcontainerTagName")
        }
    }.also {
//        println(it)
    }
}

//tasks.register("performRelease") {
//    val isCI = providers.gradleProperty("isCI")
//    doLast {
//        if (isCI.isPresent) {
//            println("Performing release actions")
//        } else {
//            throw InvalidUserDataException("Cannot perform release outside of CI")
//        }
//    }
//}
