plugins {
    id("kotlin-multiplatform")
}

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
