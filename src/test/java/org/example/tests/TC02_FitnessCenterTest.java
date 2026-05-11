package org.example.tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.base.BaseTest;
import org.example.listeners.ExtentTestListener;
import org.example.pages.IndiaMartHomePage;
import org.example.pages.IndiaMartResultsPage;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(ExtentTestListener.class)
public class TC02_FitnessCenterTest extends BaseTest {

    private static final Logger log = LogManager.getLogger(TC02_FitnessCenterTest.class);

    private IndiaMartHomePage    homePage;
    private IndiaMartResultsPage resultsPage;

    @Override
    @BeforeClass
    public void setUp() {
        super.setUp();
        log.info("=== TC02 Fitness Center Test — Setup ===");
        homePage    = new IndiaMartHomePage(driver);
        resultsPage = new IndiaMartResultsPage(driver);
        log.info("TC02 setup complete.");
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
        log.info("TC02-T1 | PASS | IndiaMart loaded. Title: {}", title);
    }

    // T2 — Does search work?
    @Test(priority = 2, dependsOnMethods = "verifyIndiaMartLoads")
    public void verifySearchReturnsResults() {
        homePage.search("Fitness Center");
        resultsPage.handlePostSearchPopup();

        int count = resultsPage.getListingsCount();
        Assert.assertTrue(count > 0,
                "Expected at least 1 listing after searching for 'Fitness Center' but found 0");
        log.info("TC02-T2 | PASS | Search returned {} result(s).", count);
    }

    // T3 — Do filters work and are listings displayed?
    @Test(priority = 3, dependsOnMethods = "verifySearchReturnsResults")
    public void verifyFiltersAndDisplayListings() {
        resultsPage.applyChennaiFilter();
        resultsPage.applyGymOnlyFilter();

        Assert.assertTrue(resultsPage.getListingsCount() > 0,
                "Expected at least 1 listing after applying filters but got none");

        resultsPage.displayAllListings();
        log.info("TC02-T3 | PASS | Filters applied. {} listing(s) found and displayed.",
                resultsPage.getListingsCount());
    }
}