# Diamond

[![Build Status](https://github.com/seepick/diamond/actions/workflows/main.yml/badge.svg)](https://github.com/seepick/diamond/actions/workflows/main.yml)

"_Given a very long time and even far more pressure, and even out of the most common shit there will be a precious
diamond created._"

This is a sample project of a backend service using modern technologies (2025) and (to my current knowledge) all the
best practices (software design/architecture, testing, code quality, CI/CD, etc).

A general technical overview can be found in the SAD (Software Architecture Document).

All (design/tech/process) decisions are documented in
so-called [ARD](https://github.com/joelparkerhenderson/architecture-decision-record)s; see:
`/doc/ArchitectureDecisionRecords/*.md`

## Dev Instructions

### Setup

* IntelliJ detekt plugin; use config/detekt.yml

### Usage

* run `nl.uwv.smz.diamond.app.LocalDiamondApp`
* Postman collections: `/local/Diamond.postman_collection.json`

## Philosophy

* code first
    * no annotations, strings, properties, yamls... just code
* be in control
    * no classpath scanning, reflection, other look-ups, no auto-magically something
    * avoid intrusive frameworks (the systems serves us, we don't serve the system)
* functional
    * pure functions: stateless, side-effect free
    * code as named expressions
    * prefer single-expression-method `fun foo() = doSome().doOther().also { done(it) }`

## Coded UML

@startuml
[Component] --> Interface1
[Component] -> Interface2
@enduml
