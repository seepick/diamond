plugins {
    kotlin("jvm")
    // TODO dependency version checker
}

repositories {
    mavenCentral()
}

kotlin { compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}
