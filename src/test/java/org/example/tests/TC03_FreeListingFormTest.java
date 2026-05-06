package org.example.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.example.base.BaseTest;
import org.example.pages.IndiaMartHomePage;
import org.example.pages.IndiaMartFreeListingPage;
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
public class TC03_FreeListingFormTest extends BaseTest {

    private IndiaMartHomePage        homePage;
    private IndiaMartFreeListingPage freeListingPage;

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

        driver          = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(8));
        homePage        = new IndiaMartHomePage(driver);
        freeListingPage = new IndiaMartFreeListingPage(driver);
    }

    // ── TF-31 (YK) : Navigate to Free Listing ────────────────────────────

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
    public void clickFreeListing() {
        homePage.clickFreeListing();
        Assert.assertTrue(driver.getCurrentUrl().contains("indiamart"),
                "Should still be on IndiaMart after clicking Free Listing");
        System.out.println("Step 3 | PASS | Free Listing page reached.");
    }

    // ── TF-32 (MS) : Fill form with invalid phone ─────────────────────────

    @Test(priority = 4, dependsOnMethods = "clickFreeListing")
    public void handleT0901PopupAndEnterMobile() {
        String invalidPhone = config.getProperty("listing.invalid.phone", "234");
        freeListingPage.enterInvalidPhone(invalidPhone);
        System.out.println("Step 4 | PASS | Invalid phone '" + invalidPhone + "' entered.");
    }

    // ── TF-33 (SM) : Submit & capture error message ───────────────────────

    @Test(priority = 5, dependsOnMethods = "handleT0901PopupAndEnterMobile")
    public void captureValidationErrorMessage() {
        String error = freeListingPage.captureErrorMessage();
        Assert.assertFalse(error.isEmpty(), "An error message should have appeared for invalid phone");
        System.out.println("Step 5 | PASS | Error message captured: \"" + error + "\"");
    }

    // ── TF-34 (KC) : Navigate back to homepage ────────────────────────────

    @Test(priority = 6, dependsOnMethods = "captureValidationErrorMessage")
    public void navigateBackToHomePage() {
        freeListingPage.navigateBackToHome();
        Assert.assertTrue(driver.getTitle().toLowerCase().contains("indiamart"),
                "Should be back on IndiaMart homepage");
        System.out.println("Step 6 | PASS | Navigated back to homepage.");
    }
}
