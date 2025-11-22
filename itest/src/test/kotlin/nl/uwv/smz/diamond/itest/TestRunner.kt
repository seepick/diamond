package nl.uwv.smz.diamond.itest

import io.cucumber.junit.platform.engine.Constants
import org.junit.platform.suite.api.ConfigurationParameter
import org.junit.platform.suite.api.IncludeEngines
import org.junit.platform.suite.api.SelectPackages
import org.junit.platform.suite.api.Suite

@Suite
@IncludeEngines("cucumber")
//@SelectClasspathResource("features") // NO!
@SelectPackages("features")
@ConfigurationParameter(key = Constants.PLUGIN_PROPERTY_NAME, value = "pretty")
//@ConfigurationParameter(key = Constants.GLUE_PROPERTY_NAME, value = "nl.uwv.smz.diamond.itest.stepdefs")
class TestRunner
