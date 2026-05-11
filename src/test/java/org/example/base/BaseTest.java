package org.example.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.utils.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.time.Duration;

public class BaseTest {

    private static final Logger log = LogManager.getLogger(BaseTest.class);

    protected static WebDriver driver;

    @BeforeClass
    public void setUp() {
        log.info("Setting up test environment.");

        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(
                Duration.ofSeconds(ConfigReader.getInt("implicit.wait", 10)));
        driver.manage().timeouts().pageLoadTimeout(
                Duration.ofSeconds(ConfigReader.getInt("page.load.timeout", 30)));

        driver.get(ConfigReader.get("base.url"));
        log.info("Browser launched and navigated to: {}", ConfigReader.get("base.url"));
    }

    public static WebDriver getDriver() { return driver; }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            log.info("Browser closed. Test run complete.");
        }
    }
}