package org.example.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.example.base.BaseTest;
import org.example.pages.IndiaMartHomePage;
import org.example.pages.IndiaMartResultsPage;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class TC01_CarWashServicesTest extends BaseTest {

    private IndiaMartHomePage  homePage;
    private IndiaMartResultsPage resultsPage;

    // Override BaseTest.setUp() to use enhanced anti-bot Chrome options
    @Override
    @BeforeClass
    public void setUp() throws IOException {
        config = new Properties();
        try (FileInputStream fis = new FileInputStream("src/test/resources/config.properties")) {
            config.load(fis);
        }

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

        driver      = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(8));

        homePage    = new IndiaMartHomePage(driver);
        resultsPage = new IndiaMartResultsPage(driver);
    }

    // ── Step 1 : Open IndiaMart ───────────────────────────────────────────
    @Test(priority = 1)
    public void openIndiaMart() {
        homePage.openSite();
        String title = driver.getTitle();
        Assert.assertFalse(title.isEmpty(), "Page title should not be empty");
        System.out.println("Step 1 | PASS | IndiaMart opened. Title: " + title);
    }

    // ── Step 2 : Handle home-page popup ──────────────────────────────────
    @Test(priority = 2, dependsOnMethods = "openIndiaMart")
    public void handleHomePagePopup() {
        homePage.handlePopup();
        System.out.println("Step 2 | PASS | Home page popup check complete.");
    }

    // ── Step 3 : Search for Car Wash Service ─────────────────────────────
    @Test(priority = 3, dependsOnMethods = "handleHomePagePopup")
    public void searchCarWashService() {
        String keyword = config.getProperty("search.keyword", "Car Wash Service");
        homePage.search(keyword);
        Assert.assertTrue(driver.getCurrentUrl().contains("indiamart.com"),
                "URL should still be on IndiaMart after search");
        System.out.println("Step 3 | PASS | Searched for: " + keyword);
    }

    // ── Step 4 : Handle post-search popup ────────────────────────────────
    @Test(priority = 4, dependsOnMethods = "searchCarWashService")
    public void handlePostSearchPopup() {
        resultsPage.handlePostSearchPopup();
        System.out.println("Step 4 | PASS | Post-search popup check complete.");
    }

    // ── Step 5 : Apply Chennai-based Suppliers filter ────────────────────
    @Test(priority = 5, dependsOnMethods = "handlePostSearchPopup")
    public void applyChennaiFilter() {
        resultsPage.applyChennaiFilter();
        System.out.println("Step 5 | PASS | Chennai-based Suppliers filter applied.");
    }

    // ── Step 6 : Apply Foam Wash filter ──────────────────────────────────
    @Test(priority = 6, dependsOnMethods = "applyChennaiFilter")
    public void applyFoamWashFilter() {
        resultsPage.applyFoamWashFilter();
        System.out.println("Step 6 | PASS | Foam Wash filter applied.");
    }

    // ── Step 7 : Apply Vacuuming filter ──────────────────────────────────
    @Test(priority = 7, dependsOnMethods = "applyFoamWashFilter")
    public void applyVacuumingFilter() {
        resultsPage.applyVacuumingFilter();
        System.out.println("Step 7 | PASS | Vacuuming filter applied.");
    }

    // ── Step 8 : Apply On-site filter ────────────────────────────────────
    @Test(priority = 8, dependsOnMethods = "applyVacuumingFilter")
    public void applyOnsiteFilter() {
        resultsPage.applyOnsiteFilter();
        System.out.println("Step 8 | PASS | On-site filter applied.");
    }

    // ── Step 9 : Display top 5 Car Wash services ─────────────────────────
    @Test(priority = 9, dependsOnMethods = "applyOnsiteFilter")
    public void displayTop5CarWashServices() {
        resultsPage.displayTop5Listings();
        System.out.println("Step 9 | PASS | Top 5 car wash services displayed.");
    }
}
