rootProject.name = "diamond"

include(
    "app",
    "view:view-model",
    "view:view-routing",
    "view:controller-api",
    "view:controller-impl",
    "client-sdk",
    "client-sdk:client-sdk-test",
    "domain:domain-logic-api",
    "domain:domain-logic-impl",
    "domain:domain-failure",
    "domain:domain-model",
    "persistence:persistence-api",
    "persistence:persistence-impl",
    "extern:extern-api",
    "extern:extern-impl",
    "extern:extern-generated",
    "extern:extern-stub",
    "shared:shared-common",
    "shared:shared-config",
    "shared:shared-wiremock",
    "shared:shared-logging",
    "shared:shared-test",
    "shared:shared-testKtor",
    "shared:openapi-gen",
    "shared:gradle-platform",
    "shared:kaml",
    "shared:kaml:kaml-core",
    "shared:kaml:kaml-github",
    "itest",
    "etest",
    ":doc:SoftwareDocument",
)

dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories {
        mavenLocal()
        mavenCentral()
    }
}
