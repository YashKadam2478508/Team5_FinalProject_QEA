package org.example.tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.base.BaseTest;
import org.example.listeners.ExtentTestListener;
import org.example.pages.IndiaMartHomePage;
import org.example.pages.IndiaMartResultsPage;
import org.example.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.List;

@Listeners(ExtentTestListener.class)
public class TC01_CarWashServicesTest extends BaseTest {

    private static final Logger log = LogManager.getLogger(TC01_CarWashServicesTest.class);

    private IndiaMartHomePage homePage;
    private IndiaMartResultsPage resultsPage;

    @Override
    @BeforeClass
    public void setUp() {
        super.setUp();
        log.info("=== TC01 CarWash Services Test — Setup ===");
        homePage = new IndiaMartHomePage(driver);
        resultsPage = new IndiaMartResultsPage(driver);
        log.info("TC01 setup complete.");
    }

    // T1 — Is the site up?
    @Test(priority = 1)
    public void verifyIndiaMartLoads() {
        homePage.openSite();
        homePage.handlePopup();

        String title = driver.getTitle();

        Assert.assertTrue(driver.getCurrentUrl().contains("indiamart.com"),
                "URL should be on indiamart.com");
        log.info("TC01-T1 | PASS | IndiaMart loaded. Title: {}", title);
    }

    // T2 — Does search work?
    @Test(priority = 2, dependsOnMethods = "verifyIndiaMartLoads")
    public void verifySearchReturnsResults() {
        String keyword = ConfigReader.get("search.keyword", "Car Wash Service");
        homePage.search(keyword);
        resultsPage.handlePostSearchPopup();

        int count = resultsPage.getListingsCount();
        Assert.assertTrue(count > 0,
                "Expected at least 1 listing card after searching for '" + keyword + "' but found 0");
        log.info("TC01-T2 | PASS | Search for '{}' returned {} result(s).", keyword, count);
    }

    // T3 — Do filters work and are results displayed correctly?
    @Test(priority = 3, dependsOnMethods = "verifySearchReturnsResults")
    public void verifyFiltersAndDisplayListings() {
        resultsPage.applyChennaiFilter();
        resultsPage.applyFoamWashFilter();
        resultsPage.applyVacuumingFilter();

        Assert.assertTrue(resultsPage.getListingsCount() > 0,
                "Expected at least 1 listing after applying filters but got none");

        resultsPage.displayTop5Listings();

        log.info("TC01-T3 | PASS | Filters applied. {} listing(s) found and displayed.", resultsPage.getListingsCount());
    }
}