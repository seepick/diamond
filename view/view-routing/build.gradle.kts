plugins {
    id("diamond-kotlin-common")
    kotlin("plugin.serialization") // id("org.jetbrains.kotlin.plugin.serialization")

    // https://github.com/yahorbarkouski/todome
    // TODO experiment with todome
//    id("com.yahorbarkouski.todome") version "1.0.3" // other / listTodos
    // ./gradlew verifyTodos
    // it only fails if there is a single TODO without a due date
    // https://github.com/yahorbarkouski/todome/blob/main/todome-plugin/src/main/java/com/yahorbarkouski/todome/task/VerifyTodosTask.java
    // ./gradlew listTodos -Passignee=yahor -Psort=asc
    // ./gradlew listTodos -Passignee=yahor -Poverdue=true
}
//todome {
// cant configure for FIXME or other tasktags; cant configure regexp pattern
// cant configure files scanned (directory/file-extension)
//    // default is 'due to'
//    dueDatePrefixes = ['due to', 'deadline']
//    // default is 'dd.MM.yyyy'
//    dateFormat = 'd MMM yyyy'
//private List<String> dueDatePrefixes = List.of("due to");
//private String dateFormat = "dd.MM.yyyy";
//private String mentionSymbol = "@";
//}

dependencies {
    implementation(project(":view:view-model"))
    implementation(project(":view:controller-api"))

    implementation(Deps.logging.kotlin)

    // KTOR
    implementation(Deps.ktor.server.core)
    implementation(Deps.ktor.server.contentNegotiation)
    implementation(Deps.ktor.serialization)

    implementation(Deps.koin.ktor)

    // DB
//    implementation("org.jetbrains.exposed:exposed-core:${Versions.exposed}")
//    implementation("org.jetbrains.exposed:exposed-jdbc:${Versions.exposed}")
//    implementation("com.h2database:h2:${Versions.h2}") // TODO runtime optional; default = postgresql, dev/test = H2

    testImplementation(Deps.ktor.client.contentNegotiation)
    testImplementation(Deps.testing.kotest.junitRunner)
    testImplementation("io.mockk:mockk:1.14.6")
    testImplementation(Deps.testing.kotest.assertions)
    testImplementation(Deps.testing.kotest.property)
    testImplementation(Deps.ktor.server.testHost)
}

