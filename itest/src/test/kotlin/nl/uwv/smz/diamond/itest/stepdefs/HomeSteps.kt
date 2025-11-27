package nl.uwv.smz.diamond.itest.stepdefs

import io.cucumber.java.en.When
import nl.uwv.smz.diamond.itest.World

class HomeSteps(private val world: World) {

    @When("get homepage")
    fun `When get homepage`() {
        world.api().getHomepage()
    }

}
