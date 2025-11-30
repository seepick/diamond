package nl.uwv.smz.diamond.itest.steps

import com.jayway.jsonpath.DocumentContext
import com.jayway.jsonpath.JsonPath
import com.jayway.jsonpath.matchers.JsonPathMatchers.hasJsonPath
import io.cucumber.java8.En
import nl.uwv.smz.diamond.itest.world.World
import nl.uwv.smz.diamond.itest.world.WorldResponse
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.skyscreamer.jsonassert.JSONAssert
import org.skyscreamer.jsonassert.JSONCompareMode

class JsonLambdaSteps(private val world: World) : En {

    // global jaway json config: Configuration.setDefaults()

    init {
        Then("the response JSON {string} is {string}") { jsonPath: String, expectedValue: String ->
            world.assertJsonPathValue(jsonPath, expectedValue)
        }
        Then("the response JSON {string} is {int}") { jsonPath: String, expectedValue: Int ->
            world.assertJsonPathValue(jsonPath, expectedValue)
        }
        Then("the response JSON body is") { expected: String ->
            world.assertJsonEquals(expected)
        }
        Then("store response JSON {string} as {string}") { jsonPath: String, stateVariableName: String ->
            world.storeVariable(stateVariableName, jsonPath)
        }
    }
}

private fun <T> World.assertJsonPathValue(jsonPath: String, expectedValue: T) {
    assertThat(
        lastResponse().bodyAsJsonPath(),
        hasJsonPath(
            jsonPath,
            equalTo(expectedValue),
        ),
    )
}

private fun World.assertJsonEquals(expected: String) {
    JSONAssert.assertEquals(
        variables.process(expected),
        lastResponse().bodyAsText(),
        JSONCompareMode.STRICT,
    )
}

private fun WorldResponse.bodyAsJsonPath(): DocumentContext =
    JsonPath.parse(bodyAsText())

private fun World.storeVariable(stateVariableName: String, jsonPath: String) {
    variables[stateVariableName] = lastResponse().bodyAsJsonPath().read(jsonPath)
}
