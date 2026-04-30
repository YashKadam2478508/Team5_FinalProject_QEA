package org.example.stepdefs;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.example.utils.DriverManager;
import org.example.utils.ScreenshotUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.Arrays;

public class Hooks {

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.setExperimentalOption("excludeSwitches",
                Arrays.asList("enable-automation", "enable-logging"));
        options.setExperimentalOption("useAutomationExtension", false);
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        options.addArguments(
                "user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
                        "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36");

        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(8));
        DriverManager.setDriver(driver);
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            ScreenshotUtil.capture(DriverManager.getDriver(), scenario.getName());
            System.out.println("  [Hook] Screenshot saved for failed scenario: " + scenario.getName());
        }
        DriverManager.quitDriver();
    }
}

