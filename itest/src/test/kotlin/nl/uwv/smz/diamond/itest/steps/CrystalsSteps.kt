package nl.uwv.smz.diamond.itest.steps

import io.cucumber.java.en.Given
import io.cucumber.java.en.When
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.engine.runBlocking
import nl.uwv.smz.diamond.domain.model.CrystalCreate
import nl.uwv.smz.diamond.domain.model.Gram
import nl.uwv.smz.diamond.itest.world.World
import stepdefs.CrystalDboRow

class CrystalsSteps(private val world: World) {

    @Given("the following crystals exists in the database")
    fun `Given the following crystals exists in the database`(crystals: List<CrystalDboRow>) {
        runBlocking {
            crystals.forEach { crystalRow ->
                world.crystalRepo.insert(crystalRow.toCrystalCreate()).shouldBeRight()
            }
        }
    }

    @When("get crystals")
    fun `When get crystals`() {
        world.api().getCrystals()
        // TODO get crystals with pagination skip 4 and take 10
    }
}

private fun CrystalDboRow.toCrystalCreate() = CrystalCreate(
    weight = Gram(weight).shouldBeRight(),
)
