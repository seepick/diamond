package nl.uwv.smz.diamond.itest.stepdefs

import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import io.github.oshai.kotlinlogging.KotlinLogging.logger
import io.kotest.common.runBlocking
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.nulls.shouldNotBeNull
import io.ktor.client.statement.bodyAsText
import nl.uwv.smz.diamond.itest.World

class Homepage(private val world: World) {

    private val log = logger {}

    // TODO Given service returns "bar" ... rewire koin objects for tests with test module

    @When("get home page")
    fun `When get home page`(): Unit = runBlocking {
        log.info { "Artificial wait for 500ms" }
        world.get("/")
    }

    @Then("response status code is {int}")
    fun `then response status code is {int}`(expectedStatus: Int) {
        world.lastResponse.shouldNotBeNull().status.value shouldBeEqual expectedStatus
    }

    @Then("response body is {string}")
    fun `response body is {string}`(expectedBody: String) {
        val bodyAsText = runBlocking { world.lastResponse.shouldNotBeNull().bodyAsText() }
        bodyAsText shouldBeEqual expectedBody
    }
}
