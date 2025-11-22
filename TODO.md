Next
=========================

* FIXME: ktor test engine with dislocated cucumber
    * allow for multiple client executions
* [ ] Cucumber programatic logging (before all)
* [ ] Cucumber-JUnit test parallelization
* [ ] Koin DI
* [ ] Cucumber with koin wiring
* [ ] Gradle version catalog (TOML)? otherwise how to group dependencies ("libraries")

Backlog
-------------------------

* [ ] Investigate Cucumber with testng https://cucumber.io/docs/guides/parallel-execution#testng
* [ ] read cucumber doc: https://cucumber.io
* [ ] client SDK testing: https://ktor.io/docs/client-testing.html#test-client
* [ ] DTO mapping library (like mapstruct but for kotlin?)
* [ ] Generate Software Architecture Document with Asciidoc
* [ ] Auto version bump up
* [ ] Quality gates (build fail static code analysis; no task tags in main)
* [ ] Local reformatting (dagger, or...?)
* [ ] Branch enforcement: no direct commits to main; only via PR merge
* [ ] OpenAPI provision (swagger endpoint)
* [ ] Persistence layer (Exposed, Liquibase/H2, testcontainer)
* [ ] End-to-End tests
* [ ] Backend OpenAPI generation
* [ ] Backend WSDL generation
* [ ] OpenShift docker deploy?

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
* [x] Explore GitHub provided quality workflows (Detekt, CodeQL)
