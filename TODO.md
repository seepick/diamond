Next
=========================

* resolve view-routes test run warnings
    * Kotest autoscan is enabled.
    * SLF4J(W): No SLF4J providers were found.

* [ ] Investigate: exposed-dao vs plain?!
* [ ] Real persistence layer (Exposed, Liquibase/H2)
* [ ] Testcontainer tests with Postgres
* [ ] Static code analysis (detekt, ...)

Backlog
-------------------------

* [ ] home page returns HATEOS-like overview
* [ ] Local reformatting (editor config)
* [ ] refactor to super/sub instead of super/super-sub (hopefully no clash, if duplicate subs in different supers); also in packages (explicit and simplified)
* [ ] Config per project via some fancy kotlin mapping lib (properties, yaml, env-vars)
* [ ] provide test jars for reusable Arbs
* [ ] info endpoint (incremental build version, build timestamp); inject GITHUB into gradle,
* [ ] Write some ADRs
* [ ] bean validation (based on OpenAPI spec)
* [ ] API error handling (with client, etc.)
* [ ] Read Cucumber doc: https://cucumber.io
* [ ] DTO mapping library (like mapstruct but for kotlin?)
* [ ] Auto version bump up
* [ ] Quality gates (build fail static code analysis; no task tags in main)
* [ ] Branch enforcement: no direct commits to main; only via PR merge
* [ ] OpenAPI provision (swagger endpoint); ktor's plugin is unusable/too beta
* [ ] End-to-End tests
* [ ] Backend OpenAPI&WSDL generation (separate sub-project, make external-API depend on it)
* [ ] OpenShift docker deploy?
* [ ] Scheduler
* [ ] Explore GitHub Detekt workflow
* [ ] Explore GitHub CodeQL workflow
* [ ] Write SAD sub-projects explanation
* [ ] Postman collection
* ... persistence-impl is actually persistence-exposed ;)
* [ ] Investigate: intellij + github issue tracker
* [ ] Investigate: code reviews done in intellij
* [ ] write KDoc for general/shared stuff
* [ ] Security (authentification (username/password)+authorisation (has the rights to access endpoint)) must be done by a proxy upfront (not the macroservice
  itself; it only gets a user ID and assumes it has been done already)
* [ ] Kotlin scheduler (jobr?)
* [ ] e2e-test as standalone sub-project; only dependency to client-sdk

Low
-------------------------

* [ ] OpenTelemetry, Micrometer
* [ ] Docsify website for GitHub; see: https://iietmoon.github.io/simple-captcha-js/
* [ ] host production somewhere free (google app engine?)

Questions
-------------------------

* [ ] kotlin.Uuid or java.UUID?
* [ ] Gradle version catalog (TOML)? otherwise how to group dependencies ("libraries")
* [ ] how to name packages when in sub-sub-projects? what is the difference one or the other?
* [ ] Persistence repos, returing domain-entity (port-adapter style) or DBO (plain)?
* [ ] isolationMode = IsolationMode.InstancePerTest or default perSpec?
* [ ] persistence-stub maybe not necessary?

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
* [x] Use Arrow's either: https://proandroiddev.com/how-to-use-arrows-either-for-exception-handling-in-your-application-a73574b39d07
* [x] CRUD operations until service (ApiError handling, JSON serialization)
* [x] CRUD operations for stub persistence
* [x] Setup Postman collection
* [x] Generate Software Architecture Document with Asciidoc

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
    * BUT: crappy website, docs/ folders everywhere and not configurable
    * Spring is much better
* How to guarantee OpenAPI doc is 1:1 implemented?
* Custom code gen approach possible?!
* https://start.ktor.io/p/openapi
