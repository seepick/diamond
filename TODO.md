Next
=========================

* FIXME: ktor test engine with dislocated cucumber
    * allow for multiple client executions
* [ ] Cucumber programatic logging (before all)
* [ ] Cucumber-JUnit test parallelization
* [ ] Koin DI
* [ ] Cucumber with koin wiring
* [ ] Github Action
* [ ] Gradle version catalog (TOML)? otherwise how to group dependencies ("libraries")

Backlog
-------------------------

* [ ] Investigate Cucumber with testng https://cucumber.io/docs/guides/parallel-execution#testng
* [ ] read cucumber doc: https://cucumber.io
* [ ] client SDK testing: https://ktor.io/docs/client-testing.html#test-client
* [ ] DTO mapping library (like mapstruct but for kotlin?)
* [ ] Generate Software Architecture Document with Asciidoc
* [ ] Auto version bump up
* [ ] Quality gates (no task tags in main)
* [ ] Branch enforcement: no direct commits to main; only via PR merge
* [ ] Openapi provision (swagger endpoint)
* [ ] Database persistence layer (Exposed, Liquibase)
* [ ] End-to-End tests
* [ ] Backend OpenApi generation
* [ ] Backend WSDL generation

Done
=========================

V1
-------------------------

* [x] Create GitHub repository
* [x] Create buildSrc infrastructure: Kotlin custom gradle plugin, Versions/Dependency management
* [x] Setup Gradle multi-module setup
* [x] Get HelloWorld endpoint working (simple ktor application)
* [x] Basic Cucumber integration test
* [x] Cucumber with ktor test engine
* [x] Programmatic Logback configuration
