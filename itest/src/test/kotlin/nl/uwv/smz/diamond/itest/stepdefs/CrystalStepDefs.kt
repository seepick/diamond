package nl.uwv.smz.diamond.itest.stepdefs

import io.cucumber.java.en.When
import kotlinx.coroutines.runBlocking
import nl.uwv.smz.diamond.itest.World

class CrystalStepDefs(private val world: World) {

    @When("get crystals")
    fun `When get crystals`(): Unit = runBlocking {
        world.getCrystals()
    }
}
