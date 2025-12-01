fun `❌ single expression`(): String = "foo"

fun `✅ single expression`() = "foo"

fun `❌ db access`() {
    transactional {
        repo.update()
    }
}

fun `✅ db access`() {
    transactional {
        repo.update()
    }
}

fun Service.`❌ wither`(): Service {
    setFoo("bar")
    return this
}

fun Service.`✅ wither`() = apply {
    setFoo("bar")
}
