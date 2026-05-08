package org.example.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.base.BaseTest;
import org.example.listeners.ExtentTestListener;
import org.example.pages.IndiaMartFreeListingPage;
import org.example.pages.IndiaMartHomePage;
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
public class TC03_FreeListingFormTest extends BaseTest {

    private static final Logger log = LogManager.getLogger(TC03_FreeListingFormTest.class);

    private IndiaMartHomePage        homePage;
    private IndiaMartFreeListingPage freeListingPage;

    @Override
    @BeforeClass
    public void setUp() throws IOException {
        log.info("=== TC03 Free Listing Form Test — Setup ===");
        config = new Properties();
        try (FileInputStream fis = new FileInputStream("src/test/resources/config.properties")) {
            config.load(fis);
        }

        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");

        driver          = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(8));
        homePage        = new IndiaMartHomePage(driver);
        freeListingPage = new IndiaMartFreeListingPage(driver);
        log.info("TC03 setup complete.");
    }

    // ── TF-31 (YK) : Navigate to Free Listing ────────────────────────────

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
    public void clickFreeListing() {
        homePage.clickFreeListing();
        Assert.assertTrue(driver.getCurrentUrl().contains("indiamart"),
                "Should still be on IndiaMart after clicking Free Listing");
        log.info("Step 3 | PASS | Free Listing page reached.");
    }

    // ── TF-32 (MS) : Fill form with invalid phone ─────────────────────────

    @Test(priority = 4, dependsOnMethods = "clickFreeListing")
    public void handleT0901PopupAndEnterMobile() {
        String invalidPhone = config.getProperty("listing.invalid.phone", "234");
        freeListingPage.enterInvalidPhone(invalidPhone);
        log.info("Step 4 | PASS | Invalid phone '{}' entered.", invalidPhone);
    }

    // ── TF-33 (SM) : Submit & capture error message ───────────────────────

    @Test(priority = 5, dependsOnMethods = "handleT0901PopupAndEnterMobile")
    public void captureValidationErrorMessage() {
        String error = freeListingPage.captureErrorMessage();
        Assert.assertFalse(error.isEmpty(), "An error message should have appeared for invalid phone");
        log.info("Step 5 | PASS | Error message captured: \"{}\"", error);
    }

}
