package com.example.AgilTestautomatisering;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static com.example.AgilTestautomatisering.SeleniumTests.driver;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StepDefinition {
    @Given("SVT Play is available")
    public void svt_play_is_available() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        //options.addArguments("--headless");
        driver = new ChromeDriver(options);

        driver.get("https://www.svtplay.se/");
    }
    @When("User visits SVT PLay")
    public void user_visits_svt_p_lay() {
        driver.manage().window().maximize();
    }
    @Then("The title should be {string}")
    public void the_title_should_be(String expectedTitle) {
        var title = driver.getTitle();
        assertEquals(expectedTitle, title, "Titeln stämmer inte!");
        driver.close();
    }
}
