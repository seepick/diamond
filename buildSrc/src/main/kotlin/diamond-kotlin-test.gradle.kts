import gradle.kotlin.dsl.accessors._e0089add3ded35fdd654c9963fdc04f9.testImplementation

dependencies {
    testImplementation(project(":shared:shared-test"))

    testImplementation(Deps.testing.kotest.junitRunner)
    testImplementation(Deps.testing.kotest.assertions)
    testImplementation(Deps.testing.kotest.assertionsArrow)
    testImplementation(Deps.testing.kotest.property)
    testImplementation(Deps.testing.junit.platformSuite)
    testImplementation(Deps.testing.junit.jupiter)
    testImplementation(Deps.testing.mockk)
}

tasks.withType<Test>().configureEach {
    // to be able to run kotests
    useJUnitPlatform()
    systemProperty("kotest.framework.config.fqn", "nl.uwv.smz.diamond.shared.test.DiamondKotestProjectConfig")
}

