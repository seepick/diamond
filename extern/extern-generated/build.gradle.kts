plugins {
    id("diamond-kotlin-common")
//    id("diamond-kotlin-test")
    id("org.openapi.generator")
}

dependencies {
    val swagger_annotations_version = "1.6.6"
    val jackson_version = "2.20"
    val jackson_versionX = "2.20.1"
    val jackson_databind_nullable_version = "0.2.8"
    val jakarta_annotation_version = "1.3.5"
    val httpclient_version = "5.1.3"
    implementation("io.swagger:swagger-annotations:$swagger_annotations_version")
    implementation("com.google.code.findbugs:jsr305:3.0.2")
    api("org.apache.httpcomponents.client5:httpclient5:$httpclient_version")
    implementation("com.fasterxml.jackson.core:jackson-core:$jackson_versionX")
    implementation("com.fasterxml.jackson.core:jackson-annotations:$jackson_version")
    implementation("com.fasterxml.jackson.core:jackson-databind:$jackson_versionX")
    implementation("com.fasterxml.jackson.jaxrs:jackson-jaxrs-json-provider:$jackson_versionX")
    implementation("org.openapitools:jackson-databind-nullable:$jackson_databind_nullable_version")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jackson_versionX")
    // com.fasterxml.jackson.datatype.jsr310.JavaTimeFeature
    implementation("jakarta.annotation:jakarta.annotation-api:$jakarta_annotation_version")
    // for OpenAPI generated client
//    implementation(Deps.ktor.client.core)
//    implementation(Deps.ktor.client.contentNegotiation)
//    implementation(Deps.ktor.io)
//    testImplementation("org.wiremock:wiremock:3.13.2")
}

// see: https://blog.codersee.com/generate-kotlin-client-from-openapi-specs/

// openApiGenerate– to generate code via Open API Tools Generator for Open API 2.0 or 3.x specification documents,
// openApiGenerators– to lists generators available via Open API Generators,
// openApiMeta– to generate a new generator to be consumed via Open API Generator,
// openApiValidate– which we can use to validate an Open API 2.0 or 3.x specification document.

// TODO generation should happen in an external library (different git repo)
// actually... the service-providers should provide it to us ;)
openApiGenerate {
    // no way to disable donation banner :-/
    // see: https://github.com/OpenAPITools/openapi-generator/issues/8982
    inputSpec.set("${project.projectDir}/src/main/specs/postsAPI.yml")
    generatorName.set("java")
    // https://openapi-generator.tech/docs/generators/java/
    // TODO which OpenAPI library to use? feign? okhttp-gson? jersey3?
    library.set("apache-httpclient")
}

sourceSets {
    main {
        java.srcDir("${layout.buildDirectory.get()}/generate-resources/main/src/main/java")
    }
}

tasks.compileKotlin {
    dependsOn("openApiGenerate")
}
