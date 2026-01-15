package com.testtechnique.todoservice.bdd;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;


@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
<<<<<<< HEAD
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.testtechnique.todoservice.bdd")
=======
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.testtechnique.todoservice.bdd.steps")
>>>>>>> 95973b7 (Refactoring et ajout des tests)
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty")
public class CucumberIT {
}
