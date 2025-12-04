plugins {
    id("diamond-kotlin-common")
    kotlin("plugin.serialization")
    id("org.openapi.generator")

    id("diamond-kotlin-test")
    id("java-test-fixtures") // TODO could also generate test-fixtures by custom openAPI generator
}

dependencies {
    api(project(":domain:domain-failure"))
    implementation(Deps.serializationx)
// `./gradlew :shared:openapi-gen:publishToMavenLocal`
    implementation("nl.uwv.smz.diamond:openapi-gen:0")
//    implementation(project(":shared:openapi-gen"))

    testImplementation(Deps.testing.jsonAssert)
    testFixturesApi(project(":shared:shared-test"))
    testFixturesApi(Deps.testing.kotest.property)
}

// execute manually: `./gradlew :view:view-model:openApiGenerate`
openApiGenerate {
    inputSpec.set(diamondOpenApiPath)
    generatorName.set("diamond-model") // see: nl.uwz.smz.diamond.openapigen.DiamondModelGenerator

    outputDir.set(projectDir.absolutePath)
    packageName.set("nl.uwv.smz.diamond.view.model.generated")
//    apiPackage.set("apiGen")
//    modelPackage.set("modelGen")
}

tasks.openApiGenerate {
    doLast {
        // TODO how to disable codegen to write those?!
        delete(
            "$projectDir/.openapi-generator-ignore",
            "$projectDir/.openapi-generator/",
        )
    }
}
