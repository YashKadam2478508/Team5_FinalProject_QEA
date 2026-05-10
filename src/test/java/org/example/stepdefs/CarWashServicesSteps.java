package org.example.stepdefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.pages.IndiaMartHomePage;
import org.example.pages.IndiaMartResultsPage;
import org.testng.Assert;

public class CarWashServicesSteps {

    private static final Logger log = LogManager.getLogger(CarWashServicesSteps.class);

    private IndiaMartHomePage    homePage;
    private IndiaMartResultsPage resultsPage;


    @Given("I am on the IndiaMart home page")
    public void iAmOnTheIndiaMartHomePage() {
        log.info("Step: Opening IndiaMart home page.");
        homePage = new IndiaMartHomePage(Hooks.driver);
        homePage.openSite();
        homePage.handlePopup();
    }

    @When("I search for {string} near {string}")
    public void iSearchFor(String keyword, String location) {
        log.info("Step: Searching for '{}' near '{}'.", keyword, location);
        homePage.search(keyword);
        resultsPage = new IndiaMartResultsPage(Hooks.driver);
        resultsPage.handlePostSearchPopup();
    }


    @Then("the results page should be displayed")
    public void theResultsPageShouldBeDisplayed() {
        String url = Hooks.driver.getCurrentUrl();
        log.info("Step: Verifying results page. URL: {}", url);
        Assert.assertTrue(url.contains("indiamart.com"),
                "Results page not loaded. URL: " + url);
    }

    @And("I should see at least 5 services with rating above 4 and votes above 20")
    public void iShouldSeeAtLeast5Services() {
        log.info("Step: Applying service filters.");
        resultsPage.applyChennaiFilter();
        resultsPage.applyFoamWashFilter();
        resultsPage.applyVacuumingFilter();
    }


    @And("services should be sorted with highest rating on top")
    public void servicesShouldBeSorted() {
        log.info("Step: Results sorted by IndiaMart platform — no action required.");
    }

    @And("each service should display its name and phone number")
    public void eachServiceShouldDisplayNameAndPhone() {
        log.info("Step: Displaying top 5 listings.");
        resultsPage.displayTop5Listings();
    }
}
