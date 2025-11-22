Which way to properly manage gradle dependencies?

Problem Description
======================

* versions repeatedly being declared, danger of mismatch (same library, different versions)
    * especially true for multi-module projects
    * leading to runtime error due to binary incompatibility (NoSuchMethodError, etc.)
* cumbersome as in lots to type and repeat (we want to be concise and fast)

Requirements
======================

* not abstracting/hiding too much (e.g. one god dependency), but be explicit/transparent enough for understanding
    * be concise enough, while capturing the essence for clarity (intuitive comprehension) sake
* not too complex, no over-engineering, just get it "good enough"
* deal with a huge amount of versions/dependencies/plugins
* custom plugin declaration (to share common code as extensions/compositions) can use it as well

Alternatives
======================

1) buildSrc custom code
2) gradle's version catalog

Custom `buildSrc` Code
----------------------
Pros:

* it's plain kotlin code with all its advantages
    * refactoring safe
    * immediate feedback from compiler if something is wrong
    * go to source with CTRL+click (javadoc)
* full control and flexibility (custom made solution)
    * e.g. group versions and dependencies
* already used for custom plugins
    * BUT: it is NOT possible to use things from the catalog for those

Cons:

* it's custom made for something a default solution already exists
* doesn't support view usages

Gradle Version Catalog
----------------------

* https://docs.gradle.org/current/userguide/version_catalogs.html

Pros:

* it's a out-of-the-box supported solution, people know it, well documented
* allows for grouped dependencies, so-called libraries

Cons:

* TOML syntax is not as useful as code (auto-completion, type feedback, etc.)
* going into source implementation/definition not possible (CTRL+click), cumbersome
* if changing/refactoring, then step-by-step errors by each build, cumbersome

Conclusion
======================

* both support grouping and a nested-hierarchy to control a vast amount of declarations
* both support auto-completion
* custom plugin declaration is fucked
    * neither the custom code approach, nor the version catalog supports it (too early in build phase i assume)
