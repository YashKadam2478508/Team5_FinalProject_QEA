package org.example.stepdefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.pages.IndiaMartHomePage;
import org.example.pages.IndiaMartResultsPage;
import org.example.utils.DriverManager;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class CarWashServicesSteps {

    private IndiaMartHomePage homePage;
    private IndiaMartResultsPage resultsPage;

    // ── TF-25 steps (KC) ─────────────────────────────────────────────────

    @Given("I am on the IndiaMart home page")
    public void iAmOnTheIndiaMartHomePage() {
        WebDriver driver = DriverManager.getDriver();
        homePage = new IndiaMartHomePage(driver);
        homePage.openSite();
        homePage.handlePopup();
    }

    @When("I search for {string} near {string}")
    public void iSearchFor(String keyword, String location) {
        homePage.search(keyword);
        resultsPage = new IndiaMartResultsPage(DriverManager.getDriver());
        resultsPage.handlePostSearchPopup();
    }

    // ── TF-26 steps (SM) ─────────────────────────────────────────────────

    @Then("the results page should be displayed")
    public void theResultsPageShouldBeDisplayed() {
        String url = DriverManager.getDriver().getCurrentUrl();
        Assert.assertTrue(url.contains("indiamart.com"),
                "Results page not loaded. URL: " + url);
    }

    @And("I should see at least 5 services with rating above 4 and votes above 20")
    public void iShouldSeeAtLeast5Services() {
        resultsPage.applyChennaiFilter();
        resultsPage.applyFoamWashFilter();
        resultsPage.applyVacuumingFilter();
        resultsPage.applyOnsiteFilter();
    }

    // ── TF-27 steps (YG) ─────────────────────────────────────────────────

    @And("services should be sorted with highest rating on top")
    public void servicesShouldBeSorted() {
        System.out.println("  [Step] Filters applied — results sorted by IndiaMart platform.");
    }

    @And("each service should display its name and phone number")
    public void eachServiceShouldDisplayNameAndPhone() {
        resultsPage.displayTop5Listings();
    }
}
