rootProject.name = "diamond"

include(
    "app",
    "view",
    "logic",
    "logic:logic-api",
    "logic:logic-impl",
    "persistence",
    "persistence:persistence-api",
    "persistence:persistence-impl",
    "extern",
    "extern:extern-api",
    "extern:extern-impl",
    "extern:extern-stub",
    "shared",
    "shared:shared-commons",
    "shared:shared-logging",
    "itest",
)

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}
