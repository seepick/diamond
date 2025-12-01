Requirements
==

* programmatic (source code, version trackable)
    * but high barrier entry
    * no WYSIWYG, thus not suitable for complex (and "pretty") diagrams
    * defined along adoc
* easy access (no visio)
    * user/rights/auth
    * web based

Options
==

* plantuml
* mermaid
* ...?

PlantUml
==

Install `dot` binary from https://graphviz.org/download/ to be able to build diagrams with code.

asciidoc:

```asciidoc
[plantuml,inlineUml,svg]
....
@startuml
[Inline] --> Interface1
[Inline] -> Interface2
@enduml
....

Or ncluded:

[plantuml,includedUml,svg]
....
include::includes/diagram.puml[]
....
```

With markdown (GitHub) also PlantUML possible "somehow":
https://gist.github.com/noamtamim/f11982b28602bd7e604c233fbe9d910f
