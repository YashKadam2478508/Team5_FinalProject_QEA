package org.example.stepdefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.pages.IndiaMartHomePage;
import org.example.pages.IndiaMartResultsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;

public class FitnessGymMenuSteps {

    private IndiaMartHomePage    homePage;
    private IndiaMartResultsPage resultsPage;
    private List<WebElement>     gymItems;

    // Note: "Given I am on the IndiaMart home page" is already defined
    // in CarWashServicesSteps — Cucumber reuses it automatically.

    @When("I navigate to the {string} menu category")
    public void iNavigateToMenuCategory(String category) {
        homePage = new IndiaMartHomePage(Hooks.driver);
        homePage.search(category);
        resultsPage = new IndiaMartResultsPage(Hooks.driver);
        resultsPage.handlePostSearchPopup();
    }

    @And("I select {string} from the Fitness sub-categories")
    public void iSelectFromFitnessSubCategories(String subCategory) {
        resultsPage.applyGymOnlyFilter();
        System.out.println("  [Step] '" + subCategory + "' filter applied.");
    }

    @Then("I should see a list of Gym sub-menu items")
    public void iShouldSeeListOfGymSubMenuItems() {
        new org.openqa.selenium.support.ui.WebDriverWait(Hooks.driver, java.time.Duration.ofSeconds(10))
                .until(org.openqa.selenium.support.ui.ExpectedConditions
                        .presenceOfElementLocated(By.cssSelector(".card.brs5")));
        gymItems = Hooks.driver.findElements(By.cssSelector(".card.brs5"));
        Assert.assertFalse(gymItems.isEmpty(), "No gym listings found on the page");
        System.out.println("  [Step] Found " + gymItems.size() + " gym items.");
    }

    @And("all sub-menu items should be stored in a collection")
    public void allSubMenuItemsShouldBeStoredInCollection() {
        Assert.assertNotNull(gymItems, "Gym items collection should not be null");
        Assert.assertFalse(gymItems.isEmpty(), "Gym items collection should not be empty");
        System.out.println("  [Step] " + gymItems.size() + " items stored in collection.");
    }

    @And("each sub-menu item name should be printed to the console")
    public void eachSubMenuItemNameShouldBePrinted() {
        resultsPage.displayAllListings();
    }
}
