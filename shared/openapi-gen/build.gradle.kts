plugins {
    id("diamond-kotlin-common")
    id("diamond-kotlin-test")
    `maven-publish`
}

dependencies {
    implementation(Deps.openapi)
    implementation(Deps.logging.kotlin)
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = group as String
            artifactId = "openapi-gen"
            version = project.version.toString()

            from(components["kotlin"])
        }
    }
}
