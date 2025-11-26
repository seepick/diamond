package nl.uwv.smz.diamond.itest.stepdefs

import io.cucumber.java.en.Then
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.nulls.shouldNotBeNull
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.runBlocking
import nl.uwv.smz.diamond.itest.World

class CommonStepDefs(private val world: World) {

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
