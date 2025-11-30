package nl.uwv.smz.diamond.itest.steps

import io.cucumber.java.en.Then
import io.kotest.matchers.equals.shouldBeEqual
import nl.uwv.smz.diamond.itest.emptyDefault
import nl.uwv.smz.diamond.itest.world.World
import nl.uwv.smz.diamond.itest.world.bodyAs
import nl.uwv.smz.diamond.view.model.PageDto

class CommonSteps(private val world: World) {

    @Then("the response status code is {int}")
    fun `Then the response status code is {int}`(expectedStatus: Int) {
        world.lastResponse().statusCode shouldBeEqual expectedStatus
    }

    @Then("the response is an empty page")
    fun `Then the response is an empty page`() {
        // must not be Any, otherwise serialization fails, so just set it to something like <String>
        world.lastResponse().bodyAs<PageDto<String>>() shouldBeEqual PageDto.emptyDefault()
    }

    @Then("the response body is {string}")
    fun `Then the response body is {string}`(expectedBody: String) {
        world.lastResponse().bodyText() shouldBeEqual expectedBody
    }
}
