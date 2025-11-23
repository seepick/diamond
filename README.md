# Diamond

[![Build Status](https://github.com/seepick/diamond/actions/workflows/main.yml/badge.svg)](https://github.com/seepick/diamond/actions/workflows/main.yml)

"_Given a very long time and even far more pressure, and even out of the most common shit there will be a precious
diamond created._"

This is a sample project of a backend service using modern technologies (2025) and (to my current knowledge) all the
best practices (software design/architecture, testing, code quality, CI/CD, etc).

It documents all its (design/tech/process) decisions in
so-called [ARD](https://github.com/joelparkerhenderson/architecture-decision-record)s; see:
`/doc/ArchitectureDecisionRecords/*.md`

## Sub-Projects

* app
* view
* logic-api
* logic-impl
* shared
* shared / logging
* itest

next:

* persistence-api
* persistence-impl
* persistence-stub (?)
* backend-api
* backend-impl (HTTP, SFTP)
* backend-stub
* backend-models (?) - generated source code (YAML, WSDL)

## Tech Stack

* Gradle
* Kotlin
* Ktor
* Koin
* Kotest
* Exposed
* Liquibase
