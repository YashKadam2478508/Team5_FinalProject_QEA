package org.example.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class IndiaMartHomePage extends BasePage {

    private static final Logger log = LogManager.getLogger(IndiaMartHomePage.class);

    public IndiaMartHomePage(WebDriver driver) {
        super(driver);
    }

    public void openSite() {
        log.info("Navigating to IndiaMart homepage.");
        driver.get("https://www.indiamart.com/");
        wait.until(d -> js.executeScript("return document.readyState").equals("complete"));
        log.info("IndiaMart homepage loaded. Title: {}", driver.getTitle());
    }

    public void handlePopup() {
        log.info("Checking for homepage popup.");
        waitAndHandlePopup();
    }

    public void search(String keyword) {
        log.info("Searching for: '{}'", keyword);
        WebElement searchBox = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='search_string']")));
        searchBox.click();
        searchBox.clear();
        searchBox.sendKeys(keyword);

        WebElement searchButton = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='btnSearch']")));
        searchButton.click();

        wait.until(d -> js.executeScript("return document.readyState").equals("complete"));
        log.info("Search complete. Current URL: {}", driver.getCurrentUrl());
    }

    public void clickFreeListing() {
        log.info("Clicking Free Listing button.");
        handlePopupIfVisible();
        WebElement freeListingBtn = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='ch_sell']")));
        safeClick(freeListingBtn);
        wait.until(d -> js.executeScript("return document.readyState").equals("complete"));
        log.info("Navigated to Free Listing page. URL: {}", driver.getCurrentUrl());
    }
}
