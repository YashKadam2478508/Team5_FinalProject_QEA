package org.example.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "Features",
        glue     = "org.example.stepdefs",
        plugin   = {
                "pretty",
                "html:reports/cucumber-report.html",
                "json:reports/cucumber-report.json"
        },
        monochrome = true
)
public class CucumberTestRunner extends AbstractTestNGCucumberTests {
}
