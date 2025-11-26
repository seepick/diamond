Next
=========================

Backlog
-------------------------

* [ ] Docker-compose
* [ ] Production ready DB (inject properties via env)
* [ ] Hikari connection pooling
* [ ] Build docker image (Gradle profile)
* [ ] need a spin up test (does the assembled JAR work)
* [ ] object mapper a la structmap for kotlin
* [ ] at startup: log BANNER, incl. version, branch, build time
* [ ] use the client SDK to also write tests (implicitly testing it)
* [ ] production ready logging (file appender)
* [ ] Release process: trigger build on GitHub, it will build+verify, then tag (version number), rebuild, publish/deploy
* [ ] home page returns HATEOS-like overview
* [ ] Local reformatting (editor config)
* [ ] refactor to super/sub instead of super/super-sub (hopefully no clash, if duplicate subs in different supers); also
  in packages (explicit and simplified)
* [ ] Parallelize Kotest tests
* [ ] info endpoint (build version & timestamp); inject GITHUB into gradle
* [ ] Write some ADRs
* [ ] bean validation (based on OpenAPI spec)
* [ ] API error handling (with client, etc.)
* [ ] Read Cucumber doc: https://cucumber.io
* [ ] DTO mapping library (like mapstruct but for kotlin?)
* [ ] Auto version bump up
* [ ] Fine tune detekt rules
* [ ] More static code analysis (higher level like PMD)
* [ ] Provide metrics report about statistics/quality, etc.
* [ ] Ensure no tasktags/todos
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
* [ ] Security (authentification (username/password)+authorisation (has the rights to access endpoint)) must be done by
  a proxy upfront (not the macroservice
  itself; it only gets a user ID and assumes it has been done already)
* [ ] Kotlin scheduler (jobr?)
* [ ] e2e-test as standalone sub-project using Karate (Gradle profile)
* [ ] can asciidoc eat ADR-md files? at least create create PDF out of them
* [ ] investigate property files needed, or config in code (env properties); same with logback
* [ ] arrow optics to manipulate deep nested immutable data https://arrow-kt.io/learn/immutable-data/
* [ ] health endpoint (ping all backends available, maybe response time)
* [ ] generate war/docker image gradle task (document in readme.md)
* [ ] use diagram code declaration in SAD/ADRs (PlantUML? supported by github?)
* [ ] configure owasp (create gradle profile, document it in README.md)

Low
-------------------------

* [ ] AsciiDoc needs some love
* [ ] more hateos-like endpoints
* [ ] circuit breaker (external services and also DB)? arrow.
* [ ] Investigate: intellij + github issue tracker
* [ ] Investigate: code reviews done in intellij
* [ ] write KDoc for general/shared stuff
* [ ] OpenTelemetry, Micrometer
* [ ] Docsify website for GitHub; see: https://iietmoon.github.io/simple-captcha-js/
* [ ] host production somewhere free (google app engine?)
* [ ] support HTTP caching? ETag (entity-tag values)

No!
-------------------------

* Whitelabel implementation for FE devs
    * same API but full control of data (data setup wizard and endpoints, choose set of predefined constellation)

Open Questions
=========================

* [ ] kotlin.Uuid or java.UUID?
* [ ] Gradle version catalog (TOML)? otherwise how to group dependencies ("libraries")
* [ ] how to name packages when in sub-sub-projects? what is the difference one or the other?
    * `:persistence:persistence-impl` vs `:persistence:impl` (verbosity vs potential name-clash)
* [ ] Persistence repos, returing domain-entity (port-adapter style) or DBO (plain)?
* [ ] isolationMode = IsolationMode.InstancePerTest or default perSpec?
* [ ] persistence-stub maybe not necessary? YES, most likely drop it
* [ ] classes in *-impl make the internal, or good enough? (pollutes code like final...)
* [ ] should the build be optimized for prod (local dev cumbersome) or for local dev (prod error-prone if not careful)
    * autoamted tests should cover for prod issues, so developer convience has precedence
    * how about external-impl and external-stub: better not have both! (not *-stub in PROD)

Challenges
=========================


Ktor Cucumber Tests
-------------------------

* testApplication{} only provided; no way to dislocated startup and shutdown :-(
* maybe startup more heavy-weight instance, with TestEngine... but then how to get the client wired (without HTTP).
* no HTTP would be great (no port assignment); also regarding parallelization (remember: there will be MANY itests)

Parallel Cucumber Tests
-------------------------

* ktor is doable; koin is stuck within with global static state :(
    * also in the future, isolate in-memory DB
* cucumber AND junit provides parallel test infra
* use thread local?
* https://cucumber.io/docs/guides/parallel-execution/#junit-5
* https://jadarma.github.io/blog/posts/2024/03/parallel-integration-tests-with-ktor/
* parallel tests in general (also for kotest); start right away with it! (isolation mode solves a lot; cheating, hehe)

OpenAPI and Ktor
-------------------------

* Experimental support (finally) exists
    * BUT: crappy website, docs/ folders everywhere and not configurable
    * Spring is much better
* How to guarantee OpenAPI doc is 1:1 implemented?
* Custom code gen approach possible?!
* https://start.ktor.io/p/openapi

Done
=========================

V1
-------------------------

* [x] Create GitHub repository
* [x] Create buildSrc infrastructure: Kotlin custom gradle plugin, Versions/Dependency management
* [x] Static code analysis with detekt (reports checkstyle, but supports @Suppress; YES!)
* [x] Setup Gradle multi-module setup
* [x] Get HelloWorld endpoint working (simple Ktor application)
* [x] Basic Cucumber integration test
* [x] Cucumber Ktor integration
* [x] Programmatic Logback configuration
* [x] GitHub Action (CI verify on push, CD on tags)
* [x] Koin DI
* [x] Cucumber programatic logging (before all)
* [x] Use Arrow's either
* [x] CRUD operations until service (ApiError handling, JSON serialization)
* [x] CRUD operations for stub persistence
* [x] Setup Postman collection
* [x] Generate Software Architecture Document with Asciidoc
* [x] Test fixtures depedency (arrow, kotest; reusable arbs)
* [x] Runtime configuration for project (env-vars via hoplite)
* [x] Generate configuration report (list of env-vars for Ops-people)
* [x] Liquibase database migration
* [x] Exposed persistence layer (exposed-dao vs plain)
* [x] Testcontainers tests with Oracle; custom gradle profile to activate (kotest tags)
