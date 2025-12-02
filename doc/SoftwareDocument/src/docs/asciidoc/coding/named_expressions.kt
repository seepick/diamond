fun `BAD single expression`(): String = "foo"

fun `GOOD single expression`() = "foo"

fun `BAD db access`() {
    transactional {
        repo.update()
    }
}

fun `GOOD db access`() {
    transactional {
        repo.update()
    }
}

fun Service.`BAD wither`(): Service {
    setFoo("bar")
    return this
}

fun Service.`✅ wither`() = apply {
    setFoo("bar")
}
