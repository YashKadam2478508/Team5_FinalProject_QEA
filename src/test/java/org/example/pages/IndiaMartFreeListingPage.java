package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class IndiaMartFreeListingPage extends BasePage {

    public IndiaMartFreeListingPage(WebDriver driver) {
        super(driver);
    }

    public void enterInvalidPhone(String phone) {
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
                System.out.println("  [Form] t0901 closed. Phone '" + phone + "' entered.");
            } else {
                System.out.println("  [Form] Phone '" + phone + "' entered directly.");
            }
        } catch (Exception e) {
            System.out.println("  [Form] Mobile input not accessible: " + e.getMessage());
        }
    }
    public String captureErrorMessage() {
        try {
            WebElement errorElement = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//p[@class='newlgnerr']")));
            String errorMessage = errorElement.getText().trim();
            System.out.println("  [Form] Error captured: \"" + errorMessage + "\"");
            return errorMessage;
        } catch (org.openqa.selenium.TimeoutException e) {
            System.out.println("  [Form] No error message appeared within wait time.");
            return "";
        }
    }

    public void navigateBackToHome() {
        driver.get("https://www.indiamart.com/");
        wait.until(d -> js.executeScript("return document.readyState").equals("complete"));
        System.out.println("  [Nav] Navigated back to IndiaMart homepage. Title: " + driver.getTitle());
    }

}
