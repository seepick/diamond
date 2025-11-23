Next
=========================

* [ ] !!! ktor test engine with dislocated cucumber; allow for multiple HTTP executions
    * also Given: mock backends, insert DB entries
* [ ] Client SDK testing: https://ktor.io/docs/client-testing.html#test-client
    * test with testImplementation("io.ktor:ktor-client-mock:$ktor_version")
* [ ] Local reformatting (detekt, ...)
* [ ] Cucumber programatic logging (before all)
* [ ] Cucumber-JUnit test parallelization
* [ ] Persistence layer (Exposed, Liquibase/H2, testcontainer)
* [ ] Generate Software Architecture Document with Asciidoc
* [ ] Write some ADRs

Questions
-------------------------

* [ ] Merge subprojects app and view together?
* [ ] Gradle version catalog (TOML)? otherwise how to group dependencies ("libraries")

Backlog
-------------------------

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

Challenges
=========================

Parallel Cucumber Tests
-------------------------

* ktor is doable; koin is stuck within with global static state :(
    * also in the future, isolate in-memory DB
* cucumber and junit provides parallel test infra
* use thread local?
* https://cucumber.io/docs/guides/parallel-execution/#junit-5
* https://jadarma.github.io/blog/posts/2024/03/parallel-integration-tests-with-ktor/
