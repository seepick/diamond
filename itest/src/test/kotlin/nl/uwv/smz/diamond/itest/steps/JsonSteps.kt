package nl.uwv.smz.diamond.itest.steps

import com.jayway.jsonpath.JsonPath
import com.jayway.jsonpath.matchers.JsonPathMatchers.hasJsonPath
import io.cucumber.java.en.Then
import nl.uwv.smz.diamond.itest.world.World
import nl.uwv.smz.diamond.itest.world.WorldResponse
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo

class JsonSteps(private val world: World) {
    // global jaway json config: Configuration.setDefaults()

    @Then("the response JSON {string} is {string}")
    fun `match json response`(jsonPath: String, expectedValue: String) {
        assertJsonPathValue(jsonPath, expectedValue)
    }

    @Then("the response JSON {string} is {int}")
    fun `match json response`(jsonPath: String, expectedValue: Int) {
        assertJsonPathValue(jsonPath, expectedValue)
    }

    private fun <T> assertJsonPathValue(jsonPath: String, expectedValue: T) {
        assertThat(
            world.lastResponse().bodyJsonPath(),
            hasJsonPath(
                jsonPath,
                equalTo(expectedValue),
            ),
        )
    }
}

fun WorldResponse.bodyJsonPath() =
    JsonPath.parse(bodyText())
