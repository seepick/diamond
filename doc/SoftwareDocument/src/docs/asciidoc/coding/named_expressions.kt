fun `BAD single expression`(): String = "foo"

fun `GOOD single expression`() = "foo"

fun `BAD single factory expression`(): FooDto = FooDto(
    id = 42,
)

fun `GOOD single factory expression`() = FooDto(
    id = 42,
)

fun `BAD db access`() {
    transactional {
        repo.update()
    }
}

fun `GOOD db access`() = transactional {
    repo.update()
}

fun Service.`BAD wither`(): Service {
    setFoo("bar")
    return this
}

fun Service.`GOOD wither`() = apply {
    setFoo("bar")
}
