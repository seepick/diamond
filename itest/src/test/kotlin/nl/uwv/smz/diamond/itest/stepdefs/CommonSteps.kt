package nl.uwv.smz.diamond.itest.stepdefs

import io.cucumber.java.en.Then
import io.kotest.matchers.equals.shouldBeEqual
import nl.uwv.smz.diamond.itest.World

class CommonSteps(private val world: World) {

    @Then("response status code is {int}")
    fun `then response status code is {int}`(expectedStatus: Int) {
        world.lastResponse().statusCode shouldBeEqual expectedStatus
    }

    @Then("response body is {string}")
    fun `response body is {string}`(expectedBody: String) {
        world.lastResponse().bodyText() shouldBeEqual expectedBody
    }
}
