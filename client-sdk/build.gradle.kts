plugins {
    id("kotlin-multiplatform")
}

// TODO Client SDK testing: https://ktor.io/docs/client-testing.html#test-client
// - test with testImplementation("io.ktor:ktor-client-mock:$ktor_version")
// - write basic /demo-webapp (angular, ask alex)

kotlin {
    jvm()
    js() {
        // https://kotlinlang.org/docs/js-project-setup.html#support-for-es2015-features
        // TODO kotlin KMP JS
        // browser {} or nodejs {}
    }

    // https://ktor.io/docs/client-create-multiplatform-application.html
    sourceSets {
        commonMain.dependencies {
            implementation(Deps.ktor.client.core)
            // implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
        }
        jvmMain.dependencies {
            implementation(Deps.ktor.client.cio)
        }
        // TODO javascript...

        commonTest.dependencies {
        }
    }
}
