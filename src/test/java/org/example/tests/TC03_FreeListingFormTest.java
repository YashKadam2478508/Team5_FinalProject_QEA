package org.example.tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.base.BaseTest;
import org.example.listeners.ExtentTestListener;
import org.example.pages.IndiaMartFreeListingPage;
import org.example.pages.IndiaMartHomePage;
import org.example.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(ExtentTestListener.class)
public class TC03_FreeListingFormTest extends BaseTest {

    private static final Logger log = LogManager.getLogger(TC03_FreeListingFormTest.class);

    private IndiaMartHomePage        homePage;
    private IndiaMartFreeListingPage freeListingPage;

    @Override
    @BeforeClass
    public void setUp() {
        super.setUp();
        log.info("=== TC03 Free Listing Form Test — Setup ===");
        homePage        = new IndiaMartHomePage(driver);
        freeListingPage = new IndiaMartFreeListingPage(driver);
        log.info("TC03 setup complete.");
    }

    // T1 — Is the site up?
    @Test(priority = 1)
    public void verifyIndiaMartLoads() {
        homePage.openSite();
        homePage.handlePopup();

        String title = driver.getTitle();
        Assert.assertTrue(title.toLowerCase().contains("indiamart"),
                "Page title should contain 'IndiaMart' but was: " + title);
        Assert.assertTrue(driver.getCurrentUrl().contains("indiamart.com"),
                "URL should be on indiamart.com");
        log.info("TC03-T1 | PASS | IndiaMart loaded. Title: {}", title);
    }

    // T2 — Is the Free Listing page reached?
    @Test(priority = 2, dependsOnMethods = "verifyIndiaMartLoads")
    public void verifyFreeListingPageReached() {
        homePage.clickFreeListing();

        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("sell") || currentUrl.contains("free-listing"),
                "Expected Free Listing page URL but was: " + currentUrl);
        log.info("TC03-T2 | PASS | Free Listing page reached. URL: {}", currentUrl);
    }

    // T3 — Does invalid phone show a validation error?
    @Test(priority = 3, dependsOnMethods = "verifyFreeListingPageReached")
    public void verifyValidationErrorForInvalidPhone() {
        String invalidPhone = ConfigReader.get("listing.invalid.phone", "234");
        freeListingPage.enterInvalidPhone(invalidPhone);

        String error = freeListingPage.captureErrorMessage();
        Assert.assertFalse(error.isEmpty(),
                "Expected a validation error message for invalid phone '" + invalidPhone + "' but got none");
        log.info("TC03-T3 | PASS | Validation error captured: \"{}\"", error);
    }
}