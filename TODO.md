Next
=========================

* resolve view-routes test run warnings
    * Kotest autoscan is enabled.
    * SLF4J(W): No SLF4J providers were found.

* [ ] arrow either: https://proandroiddev.com/how-to-use-arrows-either-for-exception-handling-in-your-application-a73574b39d07
* [ ] CRUD operations (persistence-stub; ApiError handling, JSON serialization)
* [ ] Persistence layer (Exposed, Liquibase/H2, testcontainer)
* [ ] view:{routing/controller-api/controller-impl/view-models}

Questions
-------------------------

* [ ] kotlin.Uuid or java.UUID?
* [ ] Gradle version catalog (TOML)? otherwise how to group dependencies ("libraries")
* [ ] how to name packages when in sub-sub-projects? what is the difference one or the other?

Backlog
-------------------------

* [ ] provide test jars for reusable Arbs
* [ ] Local reformatting (detekt, ...)
* [ ] info endpoint (incremental build version, build timestamp); inject GITHUB into gradle, properties, kotlin mapping lib
* [ ] Generate Software Architecture Document with Asciidoc
* [ ] Write some ADRs
* [ ] harvest ktor-sample project
* [ ] bean validation (based on OpenAPI spec)
* [ ] API error handling (with client, etc.)
* [ ] Read Cucumber doc: https://cucumber.io
* [ ] DTO mapping library (like mapstruct but for kotlin?)
* [ ] Auto version bump up
* [ ] Quality gates (build fail static code analysis; no task tags in main)
* [ ] Branch enforcement: no direct commits to main; only via PR merge
* [ ] OpenAPI provision (swagger endpoint)
* [ ] End-to-End tests
* [ ] Backend OpenAPI&WSDL generation (separate sub-project, make external-API depend on it)
* [ ] OpenShift docker deploy?
* [ ] Scheduler
* [ ] Explore GitHub Detekt workflow
* [ ] Explore GitHub CodeQL workflow
* [ ] Write SAD sub-projects explanation
* [ ] Postman collection

Done
=========================

V1
-------------------------

* [x] Create GitHub repository
* [x] Create buildSrc infrastructure: Kotlin custom gradle plugin, Versions/Dependency management
* [x] Setup Gradle multi-module setup
* [x] Get HelloWorld endpoint working (simple Ktor application)
* [x] Basic Cucumber integration test
* [x] Cucumber Ktor integration
* [x] Programmatic Logback configuration
* [x] GitHub Action (CI verify on push, CD on tags)
* [x] Koin DI
* [x] Cucumber programatic logging (before all)

Challenges
=========================

Parallel Cucumber Tests
-------------------------

* ktor is doable; koin is stuck within with global static state :(
    * also in the future, isolate in-memory DB
* cucumber AND junit provides parallel test infra
* use thread local?
* https://cucumber.io/docs/guides/parallel-execution/#junit-5
* https://jadarma.github.io/blog/posts/2024/03/parallel-integration-tests-with-ktor/

OpenAPI and Ktor
-------------------------

* Experimental support (finally) exists
    * Spring is much better
* How to guarantee OpenAPI doc is 1:1 implemented? Custom code gen approach again?! 
