// explicitly overrule dependency versions due to OWASP vulnerability reports

// See: https://docs.gradle.org/current/userguide/platforms.html#sec:sharing-dep-versions-between-projects
// basically like Maven's dependency management section but more sophisticated (BOM)

plugins {
    `java-platform`
}

dependencies {
    constraints {
        // OpenAPI (Handlebars)/Liquibase comes with outdated (3.17.0 and vulnerable!) commons-lang version; override:
//        api("org.apache.commons:commons-lang3:3.18.0")
        // no, need to stay with outdated due to wiremock relying on old/backwards-incompatible version :(
        // see /config/owas-suppression.xml
    }
}
