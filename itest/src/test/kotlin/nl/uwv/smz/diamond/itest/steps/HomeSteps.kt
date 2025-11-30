package nl.uwv.smz.diamond.itest.steps

import io.cucumber.java.en.When
import nl.uwv.smz.diamond.itest.world.World

class HomeSteps(private val world: World) {
    @When("get homepage")
    fun `When get homepage`() {
        world.api().getHomepage()
    }
}
