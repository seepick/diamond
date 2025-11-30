package nl.uwv.smz.diamond.itest.steps

import io.cucumber.java.en.Given
import io.cucumber.java8.En
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.engine.runBlocking
import nl.uwv.smz.diamond.domain.model.CrystalCreate
import nl.uwv.smz.diamond.domain.model.Gram
import nl.uwv.smz.diamond.itest.world.CrystalRequest
import nl.uwv.smz.diamond.itest.world.World
import stepdefs.CrystalDboRow

class CrystalRequestPrepare {
    private var skip: Int? = null
    private var take: Int? = null

    fun withPagination(skip: Int, take: Int) {
        this.skip = skip
        this.take = take
    }

    fun toRequest() = CrystalRequest(
        skip = skip,
        take = take,
    )
}

@Suppress("UseCheckOrError") // due to mutable state, check/error is not able to smartcast :-/
class CrystalsLambdaSteps(private val world: World) : En {
    init {
        When("get crystals") {
            world.api().getCrystals()
        }
        // TODO make this reusable
        var crystalRequest: CrystalRequestPrepare? = null
        When("prepare to get crystals") {
            crystalRequest = CrystalRequestPrepare()
        }
        When("with skip {int} and take {int}") { skip: Int, take: Int ->
            crystalRequest?.apply {
                withPagination(skip = skip, take = take)
            } ?: throw IllegalStateException("First need to prepare with 'When prepare to get ...'!")
        }
        When("execute prepared request") {
            crystalRequest?.also {
                world.api().getCrystals(it.toRequest())
            } ?: throw IllegalStateException("First need to prepare with 'When prepare to get ...'!")
            crystalRequest = null
        }
        // with sort ...
        // with filter ...
    }
}

class CrystalsSteps(private val world: World) {
    // TODO List not supported for lambda approach :-(
    @Given("the following crystals exists in the database")
    fun `Given the following crystals exists in the database`(crystals: List<CrystalDboRow>) {
        runBlocking {
            crystals.forEach { crystalRow ->
                world.crystalRepo.insert(crystalRow.toCrystalCreate()).shouldBeRight()
            }
        }
    }
}

private fun CrystalDboRow.toCrystalCreate() = CrystalCreate(
    weight = Gram(weight).shouldBeRight(),
)
