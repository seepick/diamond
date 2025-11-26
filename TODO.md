Next
=========================

* [ ] Info endpoint (build version & timestamp); inject GITHUB into gradle
* [ ] Kotlin scheduler (jobr?)
* [ ] SFTP lib
* [ ] Backend OpenAPI&WSDL generation (separate sub-project, make external-API depend on it)

Backlog
-------------------------

* [ ] PlantUML support for Asciidoc
* [ ] Docker-compose
* [ ] Production ready DB (inject properties via env)
* [ ] Hikari connection pooling
* [ ] Build docker image (Gradle profile)
* [ ] Object mapper a la structmap for kotlin
* [ ] Log info at startup: log BANNER, incl. version, branch, build time
* [ ] DTO mapping library (like mapstruct but for kotlin?)
* [ ] Provide swagger HTML endpoint
* [ ] Write SAD sub-projects explanation
* [ ] Write some ADRs
* [ ] e2e-test as standalone sub-project using Karate (Gradle profile)
* [ ] OpenTelemetry, Micrometer
* [ ] Write KDoc for general/shared stuff
* [ ] Parallelize Kotest tests
* [ ] Bean validation (based on OpenAPI spec)

Low
-------------------------

* [ ] Auto version bump up
* [ ] More static code analysis (higher level like PMD)
* [ ] Need a spin up test (does the assembled JAR work)
* [ ] Use the client SDK to also write tests (implicitly testing it)
* [ ] Production ready logging (file appender)
* [ ] Release process: trigger build on GitHub, it will build+verify, then tag (version number), rebuild, publish/deploy
* [ ] Local reformatting (editor config)
* [ ] Refactor to super/sub instead of super/super-sub (hopefully no clash, if duplicate subs in different supers); also
  in packages (explicit and simplified)
* [ ] Provide metrics report about statistics/quality, etc.
* [ ] Ensure no tasktags/todos
* [ ] Branch enforcement: no direct commits to main; only via PR merge
* [ ] OpenShift docker deploy?
* [ ] Explore GitHub Detekt workflow
* [ ] Explore GitHub CodeQL workflow
* [ ] Security (authentification (username/password)+authorisation (has the rights to access endpoint)) must be done by
  a proxy upfront (not the macroservice
  itself; it only gets a user ID and assumes it has been done already)
* [ ] Can Asciidoc eat ADR-md files? at least create create PDF out of them
* [ ] Arrow optics to manipulate deep nested immutable data https://arrow-kt.io/learn/immutable-data/
* [ ] Health endpoint (ping all backends available, maybe response time)
* [ ] Generate war/docker image gradle task (document in readme.md)
* [ ] Configure OWASP (create gradle profile, document it in README.md)
* [ ] Fine tune detekt rules
* [ ] Home page returns HATEOS-like overview
* [ ] AsciiDoc needs some love
* [ ] Circuit breaker (external services and also DB)? arrow.
* [ ] Investigate: intellij + github issue tracker
* [ ] Investigate: code reviews done in intellij
* [ ] Docsify website for GitHub; see: https://iietmoon.github.io/simple-captcha-js/
* [ ] Host production somewhere free (google app engine?)
* [ ] Support HTTP caching? ETag (entity-tag values)

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
