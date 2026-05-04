package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class IndiaMartHomePage extends BasePage {

    public IndiaMartHomePage(WebDriver driver) {
        super(driver);
    }

    public void openSite() {
        driver.get("https://www.indiamart.com/");
        wait.until(d -> js.executeScript("return document.readyState").equals("complete"));
        System.out.println("  [Home] IndiaMart opened. Title: " + driver.getTitle());
    }

    public void handlePopup() {
        waitAndHandlePopup();
    }

    public void search(String keyword) {
        WebElement searchBox = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='search_string']")));
        searchBox.click();
        searchBox.clear();
        searchBox.sendKeys(keyword);

        WebElement searchButton = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='btnSearch']")));
        searchButton.click();

        wait.until(d -> js.executeScript("return document.readyState").equals("complete"));
        System.out.println("  [Home] Searched: " + keyword + " | URL: " + driver.getCurrentUrl());
    }

    public void clickFreeListing() {
        handlePopupIfVisible();
        WebElement freeListingBtn = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='ch_sell']")));
        safeClick(freeListingBtn);
        wait.until(d -> js.executeScript("return document.readyState").equals("complete"));
        System.out.println("  [Home] Free Listing clicked. URL: " + driver.getCurrentUrl());
    }
}