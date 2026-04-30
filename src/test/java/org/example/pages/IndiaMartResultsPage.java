package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class IndiaMartResultsPage extends BasePage {

    public IndiaMartResultsPage(WebDriver driver) {
        super(driver);
    }

    // ── SM's methods (TF-26) : Filters ───────────────────────────────────

    public void handlePostSearchPopup() {
        waitAndHandlePopup();
        System.out.println("  [Results] Post-search popup check complete.");
    }

    public void applyChennaiFilter() {
        handlePopupIfVisible();
        WebElement checkbox = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//input[@id='Chennai-based Suppliersid']")));
        safeClick(checkbox);
        wait.until(d -> js.executeScript("return document.readyState").equals("complete"));
        System.out.println("  [Filter] Chennai-based Suppliers applied.");
    }

    public void applyFoamWashFilter() {
        handlePopupIfVisible();
        WebElement foamWash = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//span[normalize-space()='Foam Wash']")));
        safeClick(foamWash);
        wait.until(d -> js.executeScript("return document.readyState").equals("complete"));
        System.out.println("  [Filter] Foam Wash applied.");
    }

    public void applyVacuumingFilter() {
        handlePopupIfVisible();
        WebElement vacuuming = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//span[normalize-space()='Vacuuming']")));
        safeClick(vacuuming);
        wait.until(d -> js.executeScript("return document.readyState").equals("complete"));
        System.out.println("  [Filter] Vacuuming applied.");
    }

    public void applyOnsiteFilter() {
        handlePopupIfVisible();
        WebElement onsite = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//span[normalize-space()='On-site']")));
        safeClick(onsite);
        wait.until(d -> js.executeScript("return document.readyState").equals("complete"));
        System.out.println("  [Filter] On-site applied.");
    }




}