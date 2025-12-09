Kaml
=====

A Kotlin Yaml generator, using a typesafe, concise, auto-completable DSL.

Support for GitHub Actions, and future also: OpenAPI, OpenShift, and more...

TODO: example

Ideas
====

* kaml-core DSL
* extensibility: allow for totally custom yaml entries
* showcase building layer on top of DSL (reuse, reference, ...)
* support comments
* multiline run
*

```yaml
steps:
  - name: Execute script
    run: |
      chmod +x ./script.sh
      ./script.sh
```

GitHub
----

* generation modes: 1) inline 2) reuse/reference (the typical way when handwriting them)
