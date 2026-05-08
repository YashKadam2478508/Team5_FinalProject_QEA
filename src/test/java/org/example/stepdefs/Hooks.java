package org.example.stepdefs;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.utils.ScreenshotUtil;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public class Hooks {

    private static final Logger log = LogManager.getLogger(Hooks.class);

    public static WebDriver driver;

    @Before
    public void setUp() {
        log.info("Starting browser for Cucumber scenario.");
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(8));
        log.info("Browser launched successfully.");
    }

    @After
    public void tearDown(Scenario scenario) {
        if (driver != null) {
            try {
                // 1) Save PNG to disk (reports/screenshots/) for archival
                ScreenshotUtil.capture(driver, scenario.getName().replaceAll("\\s+", "_"));

                // 2) Embed into Cucumber HTML report
                byte[] png = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                scenario.attach(png, "image/png", scenario.getName());

                if (scenario.isFailed()) {
                    log.warn("Scenario FAILED: '{}' — screenshot attached.", scenario.getName());
                } else {
                    log.info("Scenario PASSED: '{}' — screenshot attached.", scenario.getName());
                }
            } catch (Exception e) {
                log.error("Failed to capture/attach screenshot: {}", e.getMessage());
            }

            driver.quit();
            driver = null;
            log.info("Browser closed after scenario.");
        }
    }
}
