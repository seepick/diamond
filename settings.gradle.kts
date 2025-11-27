rootProject.name = "diamond"

include(
    "app",
    "view",
    "view:view-model",
    "view:view-routing",
    "view:controller-api",
    "view:controller-impl",
    "client-sdk",
    "client-sdk:client-sdk-test",
    "domain",
    "domain:domain-logic-api",
    "domain:domain-logic-impl",
    "domain:domain-failure",
    "domain:domain-model",
    "persistence",
    "persistence:persistence-api",
    "persistence:persistence-impl",
    "persistence:persistence-stub",
    "extern",
    "extern:extern-api",
    "extern:extern-impl",
    "extern:extern-generated",
    "extern:extern-stub",
    "shared",
    "shared:shared-common",
    "shared:shared-config",
    "shared:shared-wiremock",
    "shared:shared-logging",
    "shared:shared-test",
    "itest",
    ":doc:SoftwareArchitectureDocument"
)

dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories {
        mavenCentral()
    }
}
