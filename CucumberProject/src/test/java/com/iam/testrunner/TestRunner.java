package com.iam.testrunner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = {"src/test/resources/Features"}, tags={"@loginTest1"}
,glue = "com.iam.stepdefinitions",
plugin = {"pretty", "html:src/test/resources/ExecutionResults/cucumber-reports"},
monochrome = true)

public class TestRunner {


}
