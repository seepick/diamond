rootProject.name = "diamond"

include(
    "app",
    "view",
    "logic-api",
    "logic-impl",
    "itest",
    "shared",
    "shared:logging",
)

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}
