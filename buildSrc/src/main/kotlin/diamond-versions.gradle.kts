import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

plugins {
    // help / dependencyUpdates
    id("com.github.ben-manes.versions")
}

tasks.withType<DependencyUpdatesTask> {
    val rejectPatterns = listOf(".*-ea.*", ".*RC", ".*M1", ".*check", ".*dev.*", ".*[Bb]eta.*", ".*[Aa]lpha.*")
        .map { Regex(it) }
    rejectVersionIf {
        rejectPatterns.any {
            it.matches(candidate.version)
        }
    }
}
