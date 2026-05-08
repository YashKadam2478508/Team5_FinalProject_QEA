package org.example.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class IndiaMartFreeListingPage extends BasePage {

    private static final Logger log = LogManager.getLogger(IndiaMartFreeListingPage.class);

    public IndiaMartFreeListingPage(WebDriver driver) {
        super(driver);
    }

    public void enterInvalidPhone(String phone) {
        log.info("Attempting to enter invalid phone number: '{}'", phone);
        boolean popupAppeared = handleT0901PopupIfAppears();

        try {
            WebElement mobileInput = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath("//input[@id='mobNo']")));
            mobileInput.click();
            mobileInput.clear();
            mobileInput.sendKeys(phone);
            mobileInput.sendKeys(org.openqa.selenium.Keys.ENTER);

            if (popupAppeared) {
                log.info("t0901 popup was closed before input. Phone '{}' entered.", phone);
            } else {
                log.info("Phone '{}' entered directly (no popup).", phone);
            }
        } catch (Exception e) {
            log.error("Mobile input field not accessible: {}", e.getMessage());
        }
    }

    public String captureErrorMessage() {
        log.info("Waiting for validation error message.");
        try {
            WebElement errorElement = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//p[@class='newlgnerr']")));
            String errorMessage = errorElement.getText().trim();
            log.info("Validation error captured: \"{}\"", errorMessage);
            return errorMessage;
        } catch (org.openqa.selenium.TimeoutException e) {
            log.warn("No error message appeared within the wait time.");
            return "";
        }
    }

}
