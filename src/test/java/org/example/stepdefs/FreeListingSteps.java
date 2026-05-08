package org.example.stepdefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.pages.IndiaMartFreeListingPage;
import org.example.pages.IndiaMartHomePage;
import org.testng.Assert;

public class FreeListingSteps {

    private IndiaMartHomePage        homePage;
    private IndiaMartFreeListingPage freeListingPage;
    private String                   errorMessage;

    // Note: "Given I am on the IndiaMart home page" is already defined
    // in CarWashServicesSteps — Cucumber reuses it automatically.

    @When("I navigate to the Free Listing registration page")
    public void iNavigateToFreeListingPage() {
        homePage = new IndiaMartHomePage(Hooks.driver);
        homePage.clickFreeListing();
        System.out.println("  [Step] Navigated to Free Listing page.");
    }

    @And("I fill the form with valid details but an invalid phone {string}")
    public void iFillFormWithInvalidPhone(String invalidPhone) {
        freeListingPage = new IndiaMartFreeListingPage(Hooks.driver);
        freeListingPage.enterInvalidPhone(invalidPhone);
        System.out.println("  [Step] Invalid phone '" + invalidPhone + "' entered.");
    }

    @And("I submit the registration form")
    public void iSubmitRegistrationForm() {
        // Submission is triggered by pressing Enter inside enterInvalidPhone()
        System.out.println("  [Step] Form submitted.");
    }

    @Then("an error message should appear for the phone field")
    public void errorMessageShouldAppear() {
        errorMessage = freeListingPage.captureErrorMessage();
        Assert.assertFalse(errorMessage.isEmpty(),
                "Expected a validation error for invalid phone number");
        System.out.println("  [Step] Validation error captured.");
    }

    @And("the captured error message text should be displayed")
    public void capturedErrorMessageShouldBeDisplayed() {
        System.out.println("  [Step] Validation error: \"" + errorMessage + "\"");
    }
}
