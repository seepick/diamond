plugins {
    id("diamond-kotlin-common")
}

dependencies {
    api(project(":view:view-model"))
    api(project(":domain:domain-failure"))
    api(Deps.arrowCore)
}
