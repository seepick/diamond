package nl.uwv.smz.diamond.itest.stepdefs

import io.cucumber.java.en.When
import nl.uwv.smz.diamond.itest.World

class CrystalsSteps(private val world: World) {

    @When("get crystals")
    fun `When get crystals`() {
        world.api().getCrystals()
    }
}
