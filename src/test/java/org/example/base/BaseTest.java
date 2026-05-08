package org.example.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class BaseTest {

    private static final Logger log = LogManager.getLogger(BaseTest.class);

    protected static WebDriver driver;
    protected Properties config;

    @BeforeClass
    public void setUp() throws IOException {
        log.info("Setting up test environment.");
        config = loadConfig();

        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

        driver.get(config.getProperty("base.url"));
        log.info("Browser launched and navigated to: {}", config.getProperty("base.url"));
    }

    private Properties loadConfig() throws IOException {
        Properties props = new Properties();
        FileInputStream fis = new FileInputStream("src/test/resources/config.properties");
        props.load(fis);
        fis.close();
        log.info("Config loaded from config.properties.");
        return props;
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
