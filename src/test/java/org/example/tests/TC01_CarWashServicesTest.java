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
public class TC01_CarWashServicesTest extends BaseTest {

    private static final Logger log = LogManager.getLogger(TC01_CarWashServicesTest.class);

    private IndiaMartHomePage    homePage;
    private IndiaMartResultsPage resultsPage;

    @Override
    @BeforeClass
    public void setUp() throws IOException {
        log.info("=== TC01 CarWash Services Test — Setup ===");
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
        log.info("TC01 setup complete.");
    }

    @Test(priority = 1)
    public void openIndiaMart() {
        homePage.openSite();
        String title = driver.getTitle();
        Assert.assertFalse(title.isEmpty(), "Page title should not be empty");
        log.info("Step 1 | PASS | IndiaMart opened. Title: {}", title);
    }

    @Test(priority = 2, dependsOnMethods = "openIndiaMart")
    public void handleHomePagePopup() {
        homePage.handlePopup();
        log.info("Step 2 | PASS | Home page popup check complete.");
    }

    @Test(priority = 3, dependsOnMethods = "handleHomePagePopup")
    public void searchCarWashService() {
        String keyword = config.getProperty("search.keyword", "Car Wash Service");
        homePage.search(keyword);
        Assert.assertTrue(driver.getCurrentUrl().contains("indiamart.com"),
                "URL should still be on IndiaMart after search");
        log.info("Step 3 | PASS | Searched for: {}", keyword);
    }

    @Test(priority = 4, dependsOnMethods = "searchCarWashService")
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
    public void applyFoamWashFilter() {
        resultsPage.applyFoamWashFilter();
        log.info("Step 6 | PASS | Foam Wash filter applied.");
    }

    @Test(priority = 7, dependsOnMethods = "applyFoamWashFilter")
    public void applyVacuumingFilter() {
        resultsPage.applyVacuumingFilter();
        log.info("Step 7 | PASS | Vacuuming filter applied.");
    }

    @Test(priority = 8, dependsOnMethods = "applyVacuumingFilter")
    public void displayTop5CarWashServices() {
        resultsPage.displayTop5Listings();
        log.info("Step 8 | PASS | Top 5 car wash services displayed.");
    }
}
