https://kotest.io/docs/framework/datatesting/data-driven-testing.html

```kotlin
class DataTest : FunSpec({
    context("some name") {
        withData(
            listOf(
                "x", 3,
            )
        ) { (string, number) ->
            // ...
        }
    }
})
```
