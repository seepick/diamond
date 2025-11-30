Requirements
=============

* for itest with cucumber, we need a library which supports something like: `$.items[3].title eq 'foobar'`
* a library which we can be passed a complex string to evaluate

Libraries
=============

* assert full JSON: https://github.com/skyscreamer/JSONassert
* regular expression support: https://fslev.github.io/json-compare/
* jayway path is an option (`com.jayway.jsonpath:json-path`)
    * it provides hamcrest matchers `com.jayway.jsonpath:json-path-assert`
