🔥📘🔥 Round up Documentation
========================================================================================================================

* run spell checker
* send to alex&shai; get feedback from them

Backlog
========================================================================================================================

Impl
------------------------------------------------------------------------------------------------------------------------

* use SyncService to actually do something meaningful (verifable in tests)
* change postsAPI to something meaningful
* make cronjob triggered-able via (secured) endpoint (for testing purposes)
* support of datetime types (HTTP, DB); exposed-java-time
* Introduce nested domain object (supported by sorting and filtering)
* Introduce second domain entity (full shared/reuse of pagination, sorting, filtering)
* simulate some heavy queries/joins
* Add branch name in `/info` endpoint (necessary for environment per feature-branch)
* Backend WSDL generation (separate sub-project, make external-API depend on it)
* Wrap the SFTP call into an IO coroutine; In general, every blocking call should put on a dispatcher.
* IDEA: Multi-tenant support

Testing
-------------------------

* use id("io.kotest")
* FIX: run testcointaners test addititively (not exclusively)
* isolationMode = IsolationMode.InstancePerTest or default perSpec?
* when test fails `gradlew check`, then also display assertion error message
* Configure jacaco XML for sonarqube
    * see: https://docs.sonarsource.com/sonarqube-cloud/enriching/test-coverage/java-test-coverage
* in itest, use programmatic tests too (mock single bean in koin; more fine control)
* Use Codecov / Coveralls (or via sonarqube?)

Quality
-------------------------

* OpenTelemetry, Micrometer `install(MicrometerMetrics) { registry = SimpleMeterRegistry() }`
* Fine tune detekt rules
* SonarQube: Coverage, detekt & ktlint, OWASP report; and https://github.com/marketplace/actions/sonarqube-badge
* Enforce quality gates (fail build; coverag DONE; sonarqube/detekt? locally & remote)
* ktlint direct or via detekt? (definitely need it to fail!) https://github.com/JLLeitschuh/ktlint-gradle
* treat ktlint warnings as errors; breaking the build
* Provide metrics report about statistics/quality, etc.
* Ensure no tasktags/todos
* OWASP OSS Index access https://ossindex.sonatype.org/doc/auth-required

Build
-------------------------

* Release process: trigger build on GitHub, it will build+verify, then tag (version number), rebuild, publish/deploy
    * Autoversion on manual release (specifcy version)
* Nightly checks quality (security/OWASP), and also multiple tests (performance/load, security)
* What if plugin-apply-false (instead buildSrc/build dep?)
    * ALSO: ./gradlew dependencyUpdates doesn't work to be applied recursively!
* ad gradle: build-logic or buildSrc for convention plugins
    * enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS") pluginManagement { includeBuild("build-logic") }
* invoke health endpoint from docker compose

Asciidoc
-------------------------

* AsciiDoc should fail the build if files not found
* Render draw.io diagrams via Gradle; do it manually in code?
    * https://github.com/laingsimon/render-diagram/blob/master/drawio-renderer/src/main/java/com/simonlaing/drawiorenderer/controllers/RenderController.java
    * what about: plantuml4idea? (graphivz/dot required)
* advanced: generate multi-page HTML (see: https://docs.asciidoctor.org/asciidoc/latest/toc/)
* how to create "list of" at the end of document? (for ADRs, for TODOs)
* emojis in asciidoc didn't work :-/
* for PDF, render table borders (now there are no borders)

Low
-------------------------

* reconfigureProdLog(): rolling file appender for PROD (or via app-config property?!)
* use fancy/colorized console output (when LocalDiamondApp; see bin/ shellscripts; possible for logging?)
* Write more KDoc to help making this code a better sample
* fix sftp docker shizzle https://hub.docker.com/r/atmoz/sftp/#providing-your-own-ssh-host-key-recommended
* remove leading slash "/" from config paths/urls
* Run testcontainers test on GitHub
* Client SDK
    * also write tests (implicitly testing it); tested just like routing-tests (no wiremock)
    * split client-models (openApi generated)
* could also generate documentation for GradleProperty.kt (as done for env vars)
* Home page returns HATEOS-like overview
* Docsify website for GitHub; see: https://iietmoon.github.io/simple-captcha-js/
* graceful shutdown when running in kubernetes/docker-compose (finish requests; block new; release resources)
* Whitelabel implementation for FE devs
    * same API but full control of data (data setup wizard and endpoints, choose set of predefined constellation)
* PlantUML support for AsciiDoc (working in IDE but not in gradle...)
* Asciidoctor and SVGs: https://docs.asciidoctor.org/asciidoc/latest/macros/image-svg/

Investigations
-------------------------

* kotlin.Uuid or java.UUID?
* JSON schema: https://json-schema.org
* GitHub test reporter: https://github.com/marketplace/actions/test-reporter
* intellij + github issue tracker
* code reviews done in intellij
* GitHub Detekt/CodeQL workflow

Open Questions
========================================================================================================================

* classes in *-impl make the internal, or good enough? (pollutes code like final...)
* should the build be optimized for prod (local dev cumbersome) or for local dev (prod error-prone if not careful)
    * autoamted tests should cover for prod issues, so developer convience has precedence
    * how about external-impl and external-stub: better not have both! (not *-stub in PROD)

Challenges
========================================================================================================================

Detekt (ktlint) config
-------------------------

* fine tuning of rules
* overlapping of detekt+ktlint
* gradle-build and intellij full in sync
* fail on any rule violation (warning); detekt+ktlint
* autoformat in intellij and gradle-build (we don't want people to not have the plugin configured, and then have them
  break the build unknowingly)

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
========================================================================================================================

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
* [x] Koin DI ("service locater")
* [x] Cucumber programatic logging (before all)
* [x] Use Arrow's either
* [x] CRUD operations until service (ApiError handling, JSON serialization)
* [x] Setup Postman collection
* [x] Generate Software Document with Asciidoc
* [x] Test fixtures depedency (arrow, kotest; reusable arbs)
* [x] Runtime configuration for project (env-vars via hoplite)
* [x] Generate configuration report (list of env-vars for Ops-people)
* [x] Liquibase database migration
* [x] Exposed persistence layer
* [x] Testcontainers tests with Oracle; custom gradle profile to activate (kotest tags)
* [x] Info endpoint displaying build version, timestamp, branch, etc
* [x] Backend OpenAPI generation in separate project extern-generated
* [x] extern-impl wiremock tests using extern-generated
* [x] Parallelize Kotest tests
* [x] detekt-ktlint (configure via editConfig); auto-format; IntelliJ plugin
* [x] SFTP client (JSch, testcontainers)
* [x] Implement extern-stub; wire for test&local app
* [x] Cucumber rewiring application through koin (extern-stub); fiddle around with its internals/intestines ;)
* [x] Cucumber table handling via Data Class Generator (java "only")
* [x] Kotlin coverage verified with kover (no jacoco)
* [x] Setup Dockerfile and docker-compose (oracle, sftp)
* [x] Implement PUT /sync endpoint which uses the sftp connection
* [x] Simple end-to-end tests with Karate (etest project); assuming running local instance
* [x] Support pagination
* [x] Support sorting
* [x] Log info at startup: banner, version, branch, build time
* [x] Health endpoint (ping all backends available, track response time)
* [x] Make use of KScript for local tools (instead bash): https://github.com/kscripting/kscript
    * NO, not supporting kotlin 2.*: https://github.com/kscripting/kscript/issues/421
* [x] Write and use OpenAPI generator for data classes with kotlinx serialization
* [x] Incorporate ADRs (migrating from MD to AsciiDoc) into Software Doc
* [x] Generate HTML (and host on GitHub pages) with AsciiDoc
* [x] Check all ADRs are used in SoftwareDoc (write unit test)
