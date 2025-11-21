rootProject.name = "diamond"

include(
	"app",
	"view",
	"logic-api",
	"logic-impl",
)

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}
