plugins {
    kotlin("multiplatform")
//    kotlin("plugin.serialization")
}

// TODO Client SDK testing: https://ktor.io/docs/client-testing.html#test-client
// - test with testImplementation("io.ktor:ktor-client-mock:$ktor_version")
// - write basic /demo-webapp (angular, ask alex)

kotlin {
    jvm()
    js {
//        // https://kotlinlang.org/docs/js-project-setup.html#support-for-es2015-features
        browser {}
        binaries.executable()
        generateTypeScriptDefinitions() // d.ts
    }

    // https://ktor.io/docs/client-create-multiplatform-application.html
//    sourceSets {
//        commonMain.dependencies {
// //            implementation(Deps.ktor.client.core)
// //            implementation(Deps.serializationx)
// //            implementation("kotlin-stdlib)
//            // implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
//        }
//        jvmMain.dependencies {
// //            implementation(Deps.ktor.client.cio)
//        }
//        jsMain.dependencies {
// //            implementation(Deps.ktor.client.?which?)
//        }
//        commonTest.dependencies {
//        }
}

// tasks.withType<KotlinJsCompile>().configureEach {
//    compilerOptions {
//        target = "es2015"
//    }
// }
