Next
=========================

* mini-playground; to try out things
    1) kotest tags (testcontainers additivity)
* e0.5e tests with docker, so not real backends
* crystal upload ftp
* crystal POST enrich from posts
* docker compose, wiremock for posts api (used in e0.5e)

* ! fix ktlint; adjust it to my style
* [ ] extern-impl wiremock tests using extern-generated
* [ ] implement extern-stub; wire for test & local app
* [ ] use extern-api in domain-logic; introduce new sample endpoint; extend tests

* [ ] Log info at startup: log BANNER, incl. version, branch, build time
* [ ] Kotlin scheduler (jobr?)
* [ ] Object mapper a la structmap for kotlin
* [ ] Backend WSDL generation (separate sub-project, make external-API depend on it)
* [ ] Write OpenAPI spec for own API

Backlog
-------------------------

* [ ] List endpoints with pagination and sorting
* [ ] make use of KScript for local tools (instead bash): https://github.com/kscripting/kscript
* [ ] FIX: run testcointaners test addititively (not exclusively)
* [ ] Production ready DB (inject properties via env)
* [ ] Hikari connection pooling
* [ ] Build docker image (Gradle profile)
* [ ] Docker-compose (app + dependencies: DB, SFTP, MQ)
* [ ] e2e-test as standalone sub-project using Karate (Gradle profile)
* [ ] Bean validation (based on OpenAPI spec)
* [ ] Write SAD sub-projects explanation
* [ ] Write some ADRs
* [ ] ktlint? https://github.com/JLLeitschuh/ktlint-gradle

Low
-------------------------

* [ ] API versioning (path based; or accept header?)
* [ ] Provide swagger HTML endpoint
* [ ] OpenTelemetry, Micrometer
* [ ] Write KDoc for general/shared stuff
* [ ] Gatling load tests: https://github.com/gatling/gatling-gradle-plugin-demo-kotlin
* [ ] Run testcontainers test on GitHub
* [ ] Use OpenAPI spec to custom-generate Ktor routing skeleton
* [ ] Use OpenAPI spec to custom-generate client (client-SDK); write full tests to verify
* [ ] PlantUML support for Asciidoc (working in IDE but not in gradle...)
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
* [ ] Explore GitHub Detekt workflow
* [ ] Explore GitHub CodeQL workflow
* [ ] Security (authentification (username/password)+authorisation (has the rights to access endpoint)) must be done by
  a proxy upfront (not the macroservice
  itself; it only gets a user ID and assumes it has been done already)
* [ ] Can Asciidoc eat (ADR) markdown files? at least create create PDF out of them
* [ ] Arrow optics to manipulate deep nested immutable data https://arrow-kt.io/learn/immutable-data/
* [ ] Health endpoint (ping all backends available, maybe response time)
* [ ] Generate war/docker image gradle task (document in readme.md)
* [ ] Configure OWASP (create gradle profile, document it in README.md)
* [ ] Fine tune detekt rules
* [ ] Home page returns HATEOS-like overview
* [ ] AsciiDoc needs some love
* [ ] Circuit breaker with arrow-fx-coroutines (external services and also DB?)
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

* [ ] how much config can be moved to code? https://12factor.net/config
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

Parallel Tests
-------------------------

* use this as a selling point: https://jadarma.github.io/blog/posts/2024/03/parallel-integration-tests-with-ktor/
* use koinApplication (not global instance!)
* install(KoinIsolated) for ktor  https://www.droidcon.com/2025/01/14/koins-isolated-context/

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
* We could always fall back to Java generated code, and use it from Kotlin ;)
* Custom code gen approach possible?!
    * use https://square.github.io/kotlinpoet/
* https://start.ktor.io/p/openapi

Done
=========================

Not Doing
-------------------------

* OpenShift docker deploy
* Add branch name in `/info` endpoint (necessary for environment per feature-branch)

V1
-------------------------

* [x] Create GitHub repository
* [x] Create buildSrc infrastructure: Kotlin custom gradle plugin, Versions/Dependency management
* [x] Static code analysis with detekt (reports checkstyle, but supports @Suppress; YES!)
    * https://medium.com/@mohamad.alemicode/enforcing-code-quality-in-android-with-detekt-and-ktlint-a-practical-guide-907b57d047ec
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
* [x] Info endpoint displaying build version, timestamp, branch, ...
* [x] Backend OpenAPI generation in separate project extern-generated
* [x] Parallelize Kotest tests
* [x] detekt-ktlint (configure via editConfig); auto-format; IntelliJ plugin
* [x] SFTP client (JSch, testcontainers)
