package com.example.AgilTestautomatisering;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/java/com/example/AgilTestautomatisering/resources")
public class RunCucumberTest {
}
