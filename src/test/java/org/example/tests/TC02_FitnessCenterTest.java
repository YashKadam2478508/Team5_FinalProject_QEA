package org.example.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.example.base.BaseTest;
import org.example.pages.IndiaMartHomePage;
import org.example.pages.IndiaMartResultsPage;
import org.example.utils.ExtentTestListener;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

@Listeners(ExtentTestListener.class)
public class TC02_FitnessCenterTest extends BaseTest {

    private IndiaMartHomePage    homePage;
    private IndiaMartResultsPage resultsPage;

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

    @Test(priority = 1)
    public void openIndiaMart() {
        homePage.openSite();
        Assert.assertFalse(driver.getTitle().isEmpty(), "Page title should not be empty");
        System.out.println("Step 1 | PASS | IndiaMart opened.");
    }

    @Test(priority = 2, dependsOnMethods = "openIndiaMart")
    public void handleHomePagePopup() {
        homePage.handlePopup();
        System.out.println("Step 2 | PASS | Home page popup check complete.");
    }

    @Test(priority = 3, dependsOnMethods = "handleHomePagePopup")
    public void searchFitnessCenter() {
        homePage.search("Fitness Center");
        Assert.assertTrue(driver.getCurrentUrl().contains("indiamart"),
                "Should be on IndiaMart search results page");
        System.out.println("Step 3 | PASS | Searched for Fitness Center.");
    }

    @Test(priority = 4, dependsOnMethods = "searchFitnessCenter")
    public void handlePostSearchPopup() {
        resultsPage.handlePostSearchPopup();
        System.out.println("Step 4 | PASS | Post-search popup check complete.");
    }

    @Test(priority = 5, dependsOnMethods = "handlePostSearchPopup")
    public void applyChennaiFilter() {
        resultsPage.applyChennaiFilter();
        System.out.println("Step 5 | PASS | Chennai-based Suppliers filter applied.");
    }

    @Test(priority = 6, dependsOnMethods = "applyChennaiFilter")
    public void applyGymOnlyFilter() {
        resultsPage.applyGymOnlyFilter();
        System.out.println("Step 6 | PASS | Gym Only filter applied.");
    }

    // ── TF-39 (YK) ─────────────────────────────────────────────────────
    // YK pastes @Test priority 7 here
}
