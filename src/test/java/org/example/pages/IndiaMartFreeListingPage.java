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
}
