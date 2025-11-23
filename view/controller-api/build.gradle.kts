plugins {
    id("diamond-kotlin-common")
}

dependencies {
    // TODO how to expose gradle transitive `api` like for projects (like for regular dependencies)
    implementation(project(":view:view-model"))
}
