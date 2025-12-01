package nl.uwv.smz.diamond.itest.steps

import io.cucumber.java.en.Given
import io.cucumber.java8.En
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.engine.runBlocking
import nl.uwv.smz.diamond.domain.model.CrystalCreate
import nl.uwv.smz.diamond.domain.model.Gram
import nl.uwv.smz.diamond.domain.model.SortDirection
import nl.uwv.smz.diamond.itest.world.CrystalRequest
import nl.uwv.smz.diamond.itest.world.World
import stepdefs.CrystalDboRow

val SortDirection.querySymbol: String
    get() = when (this) {
        SortDirection.Asc -> "+"
        SortDirection.Desc -> "-"
    }

val SortDirection.gherkinLabel: String
    get() = when (this) {
        SortDirection.Asc -> "asc"
        SortDirection.Desc -> "desc"
    }

class CrystalRequestPrepare {
    private var skip: Int? = null
    private var take: Int? = null
    private val sortFields = mutableListOf<String>()

    fun withPagination(skip: Int, take: Int) {
        this.skip = skip
        this.take = take
    }

    fun andWithSorting(field: String, direction: SortDirection?) {
        sortFields += "${direction?.querySymbol ?: ""}$field"
    }

    fun toRequest() = CrystalRequest(
        skip = skip,
        take = take,
        sorts = sortFields,
    )
}

/*
@ParameterType("Speak|Clear|Delete|Share")
public MessageBarButtonType MessageBarButtonType(String buttonType) {
  return MessageBarButtonType.valueOf(buttonType);
}

// use like this. the name inside {} should match the name of method, though I just used the type name.
@Then("Select message bar {MessageBarButtonType} button")
public void select_message_bar_button(MessageBarButtonType buttonType) {
  ...
}
 */
@Suppress("UseCheckOrError") // due to mutable state, check/error is not able to smartcast :-/
class CrystalsLambdaSteps(private val world: World) : En {
    init {
        When("get crystals") {
            world.api().getCrystals()
        }
        // TODO make this reusable
        var crystalRequest: CrystalRequestPrepare? = null

        fun withCrystalRequest(code: CrystalRequestPrepare.() -> Unit) {
            crystalRequest?.apply(code)
                ?: throw IllegalStateException("First need to prepare with 'When prepare to get ...'!")
        }
        When("prepare to get crystals") {
            crystalRequest = CrystalRequestPrepare()
        }
        When("with skip {int} and take {int}") { skip: Int, take: Int ->
            withCrystalRequest {
                withPagination(skip = skip, take = take)
            }
        }
        ParameterType<SortDirection>(
            "sortDirection",
            "(asc|desc)",
            // no, cucumber plugin won't pick it up if constructed dynamically :(
//            "(${SortDirection.entries.joinToString("|") { it.gherkinLabel }}",
        ) { string: String ->
            SortDirection.entries.firstOrNull { it.gherkinLabel == string }
                ?: error("Unknown sort '$string'! Valid: ${SortDirection.entries.joinToString { it.gherkinLabel }}")
        }
        When("with sort for {string}") { field: String ->
            withCrystalRequest {
                andWithSorting(field, null)
            }
        }
        When("with sort for {string} in direction {sortDirection}") { field: String, direction: SortDirection ->
            withCrystalRequest {
                andWithSorting(field, direction)
            }
        }
        When("execute prepared request") {
            withCrystalRequest {
                world.api().getCrystals(toRequest())
            }
            crystalRequest = null
        }
        // TODO with filter ...
    }
}

class CrystalsSteps(private val world: World) {
    // TODO List not supported for lambda approach :-( thus need to revert to regular annotation based approach.
    @Given("the following crystals exists in the database")
    fun `Given the following crystals exists in the database`(crystals: List<CrystalDboRow>) {
        runBlocking {
            crystals.forEach { crystalRow ->
                // FIXME change to have the DB inserter directly go to the CrystalTable
                // would also mean FULL control (ids) and also column names matching 1:1
                world.crystalRepo.insert(crystalRow.toCrystalCreate()).shouldBeRight()
            }
        }
    }
}

private fun CrystalDboRow.toCrystalCreate() = CrystalCreate(
    weight = Gram(weight).shouldBeRight(),
)
