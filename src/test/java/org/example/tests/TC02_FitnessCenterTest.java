package org.example.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.base.BaseTest;
import org.example.listeners.ExtentTestListener;
import org.example.pages.IndiaMartHomePage;
import org.example.pages.IndiaMartResultsPage;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

@Listeners(ExtentTestListener.class)
public class TC02_FitnessCenterTest extends BaseTest {

    private static final Logger log = LogManager.getLogger(TC02_FitnessCenterTest.class);

    private IndiaMartHomePage    homePage;
    private IndiaMartResultsPage resultsPage;

    @Override
    @BeforeClass
    public void setUp() throws IOException {
        log.info("=== TC02 Fitness Center Test — Setup ===");
        config = new Properties();
        try (FileInputStream fis = new FileInputStream("src/test/resources/config.properties")) {
            config.load(fis);
        }

        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");

        driver      = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(8));
        homePage    = new IndiaMartHomePage(driver);
        resultsPage = new IndiaMartResultsPage(driver);
        log.info("TC02 setup complete.");
    }


    @Test(priority = 1)
    public void openIndiaMart() {
        homePage.openSite();
        Assert.assertFalse(driver.getTitle().isEmpty(), "Page title should not be empty");
        log.info("Step 1 | PASS | IndiaMart opened.");
    }

    @Test(priority = 2, dependsOnMethods = "openIndiaMart")
    public void handleHomePagePopup() {
        homePage.handlePopup();
        log.info("Step 2 | PASS | Home page popup check complete.");
    }

    @Test(priority = 3, dependsOnMethods = "handleHomePagePopup")
    public void searchFitnessCenter() {
        homePage.search("Fitness Center");
        Assert.assertTrue(driver.getCurrentUrl().contains("indiamart"),
                "Should be on IndiaMart search results page");
        log.info("Step 3 | PASS | Searched for Fitness Center.");
    }

    @Test(priority = 4, dependsOnMethods = "searchFitnessCenter")
    public void handlePostSearchPopup() {
        resultsPage.handlePostSearchPopup();
        log.info("Step 4 | PASS | Post-search popup check complete.");
    }

    @Test(priority = 5, dependsOnMethods = "handlePostSearchPopup")
    public void applyChennaiFilter() {
        resultsPage.applyChennaiFilter();
        log.info("Step 5 | PASS | Chennai-based Suppliers filter applied.");
    }

    @Test(priority = 6, dependsOnMethods = "applyChennaiFilter")
    public void applyGymOnlyFilter() {
        resultsPage.applyGymOnlyFilter();
        log.info("Step 6 | PASS | Gym Only filter applied.");
    }


    @Test(priority = 7, dependsOnMethods = "applyGymOnlyFilter")
    public void displayAllListings() {
        resultsPage.displayAllListings();
        log.info("Step 7 | PASS | All fitness center listings displayed on console.");
    }
}
