plugins {
    id("diamond-kotlin-common")
    id("diamond-kotlin-test")
}

dependencies {
    implementation(project(":extern:extern-generated"))
    api(project(":domain:domain-failure"))
    // TODO or use a more recent fork of JSch? (last version from 2018!)
    // https://medium.com/whozapp/sftp-test-implem-of-jsch-with-kotlin-testcontainers-and-spring-boot-native-537f624da895
    implementation("com.github.mwiede:jsch:2.27.7") // fork of JSch
    implementation(Deps.logging.kotlin)
    // implementation("com.jcraft:jsch:0.1.55")

    testImplementation(project(":shared:shared-wiremock"))
    testImplementation(Deps.testing.testcontainers.main)
}
