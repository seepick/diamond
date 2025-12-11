Round up doc
=========================

Small
-------------------------

* TODOs.md (and tasktags) as appendix chapter outlook, future/open doings
* rework sitemap.svg
* go through all ADRs, finish them (add at least a bit)
* go through all *.adoc files, do the bare minimum again
* review HTML
* review PDF
* send to alex&shai; get feedback from them

Next
=========================

* ad gradle: build-logic or buildSrc for convention plugins
    * enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
      pluginManagement {
      includeBuild("build-logic")
      }
* use id("io.kotest")
* [ ] create testproject for cucumber + ktor test engine
    * w: file:///Users/toh/workspace/diamond/itest/src/test/kotlin/nl/uwv/smz/diamond/itest/testInfra/KtorHooks.kt:95:15
      Suppression of error 'INVISIBLE_REFERENCE' might compile and work, but the compiler behavior is UNSPECIFIED and
      WILL NOT BE PRESERVED. Please report your use case to the Kotlin issue tracker instead: https://kotl.in/issue
      Business Relevant

-------------------------

* !! finish openapi generator (client 80% done; kotlinx serialization java datetime)
* [ ] Initial DB seeding of masterdata
* [ ] Setup MessageQueue
* [ ] Bean validation (based on OpenAPI spec)
* [ ] Object mapper a la mapstruct for kotlin (optics?)
    * how does it solve the 99% default, 1% custom mapping?
* [ ] LDAP integration
* [ ] Provide swagger HTML endpoint
* [ ] OpenTelemetry, Micrometer `install(MicrometerMetrics) { registry = SimpleMeterRegistry() }`
* [ ] Backend WSDL generation (separate sub-project, make external-API depend on it)
* [ ] Check if liquibase-to-exposed generation is possible (do we even want that?! NO!)

Minor:

* [ ] Feature flag support
* [ ] Support filtering (see MWP)
* [ ] API versioning (needed with only 1 FE?)

Backlog
-------------------------

* use fancy/colorized console output (when LocalDiamondApp; see bin/ shellscripts; possible for logging?)
* when test fails `gradlew check`, then also display assertion error message
* [ ] Kotlin scheduler (jobr?); cronjob running SFTP; can be triggered via endpoint
* [ ] Configure jacaco XML for sonarqube
    * see: https://docs.sonarsource.com/sonarqube-cloud/enriching/test-coverage/java-test-coverage
* [ ] Enforce quality gates (fail build; coverag DONE; sonarqube/detekt? locally & remote)
* [ ] SonarQube Coverage
* [ ] SonarQube detekt & ktlint integration
* [ ] SonarQube badges: https://github.com/marketplace/actions/sonarqube-badge
* [ ] What if plugin-apply-false (instead buildSrc/build dep?); ALSO: ./gradlew dependencyUpdates doesn't work to be
  applied recursively!
* [ ] Autoversion on manual release (specifcy version)
* [ ] support of datetime types (HTTP, DB); exposed-java-time
* [ ] create playground cucumber and ktor testengine, parallel tests (otherwise startEmbedded full fledged?!)
    * set up playground, parallel tests (junit/kotest and cucumber) starting up isolated parts of the application (
      rewire things; @PrimaryBean vs koin-modules (overrides); and then override 2x; once in PROD, once in test, and
      BAM); capability to implement different test strategies
* [ ] Configure OWASP report (fail on too high vulns)
* better sonar integration; reports; also for OWASP https://ossindex.sonatype.org/doc/auth-required
* [ ] invoke health endpoint from docker compose
* [ ] Introduce nested domain object (supported by sorting and filtering)
* [ ] Introduce second domain entity (full shared/reuse of pagination, sorting, filtering)
* move pagination/sorting/filtering into shared-*
* change postsAPI to something meaningful
* in itest, use programmatic tests too (mock single bean in koin; more fine control)
* [ ] Setup playground subfolder with standalone porjects
    * [ ] FIX: run testcointaners test addititively (not exclusively)
    * [ ] ktor+cucumber, decoupled test application
* [ ] Write OpenAPI spec for own API (how to verify contract automatically?!)
* [ ] Introduce e0.5e (quarter of a e2e) tests with docker, so not real backends
    * [ ] Docker compose, wiremock for posts api (used in e0.5e)
* [ ] SFTP coroutine IO
* [ ] Write more KDoc to help making this code a better sample
* [ ] Karate support different environments
* [ ] Write OpenAPI generator for ktor server side (route interfaces)

Asciidoc
-------------------------

* AsciiDoc should fail the build if stuff not found
* Render draw.io diagrams via Gradle; do it manually in code?
    * https://github.com/laingsimon/render-diagram/blob/master/drawio-renderer/src/main/java/com/simonlaing/drawiorenderer/controllers/RenderController.java
    * what about: plantuml4idea? (graphivz/dot required)
* advanced: generate multi-page HTML (see: https://docs.asciidoctor.org/asciidoc/latest/toc/)
* emojis in asciidoc didn't work :-/
* how to create "list of" at the end of document (for ADRs, for TODOs)

Low
-------------------------

* rework build pipeline diagram: sketch future desired; layout vertically (not horizontally)
* Enable OSS Index analyzer, authentication required: https://ossindex.sonatype.org/doc/auth-required
* maybe use arrow's optics to manipulate deep nested, immutable data classes.
* Nightly build not only more quality analysis (security/OWASP) but also different test types (performance/load/stress,
  security)
* [ ] reconfigureProdLog(): rolling file appender for PROD (or via app-config property?!)
* [ ] Hikari connection pooling
* fix sftp docker shizzle https://hub.docker.com/r/atmoz/sftp/#providing-your-own-ssh-host-key-recommended
* use SyncService to actually do something meaningful (verifable in tests)
* [ ] ktlint direct or via detekt? (definitely need it to fail!) https://github.com/JLLeitschuh/ktlint-gradle
* [ ] remove leading slash "/" from config paths/urls
* [ ] treat ktlint warnings as errors; breaking the build
* [ ] store BSN as a number in DB (heavy queries/joins)
* [ ] Gatling load tests: https://github.com/gatling/gatling-gradle-plugin-demo-kotlin
    * also with karate available: https://github.com/karatelabs/karate/tree/master/karate-gatling
* [ ] Run testcontainers test on GitHub
* [ ] Use OpenAPI spec to custom-generate Ktor routing skeleton
* [ ] Use OpenAPI spec to custom-generate client (client-SDK); write full tests to verify
* [ ] Auto version bump up
* [ ] More static code analysis (higher level like PMD)
* [ ] Need a spin up test (does the assembled JAR work)
* [ ] Use the client SDK to also write tests (implicitly testing it)
* [ ] Production ready logging (file appender)
* [ ] Release process: trigger build on GitHub, it will build+verify, then tag (version number), rebuild, publish/deploy
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
* [ ] Arrow optics to manipulate deep nested immutable data https://arrow-kt.io/learn/immutable-data/
* [ ] Generate war/docker image gradle task (document in readme.md)
* [ ] Configure OWASP (create gradle profile, document it in README.md)
* [ ] Fine tune detekt rules
* [ ] Home page returns HATEOS-like overview
* [ ] Circuit breaker with arrow-fx-coroutines (external services and also DB?)
* [ ] Investigate: intellij + github issue tracker
* [ ] Investigate: code reviews done in intellij
* [ ] Docsify website for GitHub; see: https://iietmoon.github.io/simple-captcha-js/
* [ ] Host production somewhere free (google app engine?)
* [ ] Support HTTP caching? ETag (entity-tag values)
* [ ] Liquibase gradle plugin? What for? Generating stuff? e.g. liquibaseRuntime("org.postgresql:postgresql:42.2.23")
* [ ] Sonarqube gradle plugin...
* [ ] graceful shutdown when running in kubernetes/docker-compose (finish current requests; block new ones; release
  resources)
* [ ] client-sdk tested just like routing-tests (no wiremock)
* [ ] client-sdk split client-models (openApi generated; custom generator? create playground); use client-models in
  separate (own repo) e2e tests
* [ ] Investigate JSON schema: https://json-schema.org
* [ ] could also generate config doc (just as done for app config (ENV vars)), do so as well for GradleProperty.kt (
  always up2date doc)

No!
-------------------------

* custom Dockerfile to build image
* crystal upload ftp
* Whitelabel implementation for FE devs
    * same API but full control of data (data setup wizard and endpoints, choose set of predefined constellation)
* there is no persistence-stub (in-memory DB is fast enough and we are in full control of it; thus no reason)

Unimportant
-------------------------

* PlantUML support for AsciiDoc (working in IDE but not in gradle...)
* Asciidoctor and SVGs: https://docs.asciidoctor.org/asciidoc/latest/macros/image-svg/

When going real Real
-------------------------

* switch cucumber-en to cucumber-nl
* investigate GitHub test reporter: https://github.com/marketplace/actions/test-reporter

Open Questions
=========================

* [ ] exposed DSL or DAO
  approach? https://stackoverflow.com/questions/70734941/exposed-orm-dsl-vs-dao-in-many-to-many-relationships-best-practices
* [ ] how much config can be moved to code? https://12factor.net/config
* [ ] kotlin.Uuid or java.UUID?
* [ ] Gradle version catalog (TOML)? otherwise how to group dependencies ("libraries")
* [ ] how to name packages when in sub-sub-projects? what is the difference one or the other?
    * `:persistence:persistence-impl` vs `:persistence:impl` (verbosity vs potential name-clash)
        * => YES< clear, verbosity wins! otherwise there WILL BE name clashes!
* [ ] Persistence repos, returing domain-entity (port-adapter style) or DBO (plain)?
* [ ] isolationMode = IsolationMode.InstancePerTest or default perSpec?
* [ ] classes in *-impl make the internal, or good enough? (pollutes code like final...)
* [ ] should the build be optimized for prod (local dev cumbersome) or for local dev (prod error-prone if not careful)
    * autoamted tests should cover for prod issues, so developer convience has precedence
    * how about external-impl and external-stub: better not have both! (not *-stub in PROD)

Challenges
=========================

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
* [ ] Register Sonartype OSS Index (PAT) for faster OWASP check
