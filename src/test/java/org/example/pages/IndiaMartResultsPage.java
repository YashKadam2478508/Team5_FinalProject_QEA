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


    // ── YG's methods (TF-27) : Display ───────────────────────────────────

    public void displayTop5Listings() {
        List<WebElement> cards = driver.findElements(By.cssSelector(".card.brs5"));
        System.out.println("\n===== CAR WASHING SERVICES IN CHENNAI =====");
        if (cards.isEmpty()) {
            System.out.println("No results found. URL: " + driver.getCurrentUrl());
        } else {
            int count = Math.min(5, cards.size());
            for (int i = 0; i < count; i++) {
                try {
                    String serviceName;
                    try {
                        serviceName = cards.get(i)
                                .findElement(By.cssSelector(".producttitle a.cardlinks"))
                                .getText().trim();
                    } catch (Exception e) { serviceName = "N/A"; }

                    String companyName;
                    try {
                        companyName = cards.get(i)
                                .findElement(By.cssSelector(".companyname a.cardlinks"))
                                .getText().trim();
                    } catch (Exception e) { companyName = "N/A"; }

                    String rating;
                    try {
                        rating = cards.get(i).getAttribute("data-rating");
                        if (rating == null || rating.isEmpty()) rating = "N/A";
                    } catch (Exception e) { rating = "N/A"; }

                    String location;
                    try {
                        location = cards.get(i)
                                .findElement(By.cssSelector(".highlight"))
                                .getText().trim();
                    } catch (Exception e) { location = "N/A"; }

                    System.out.println((i + 1) + ". Service  : " + serviceName);
                    System.out.println("   Company  : " + companyName);
                    System.out.println("   Rating   : " + rating);
                    System.out.println("   Location : " + location);
                    System.out.println("   ---");

                } catch (Exception e) {
                    System.out.println((i + 1) + ". Error reading card: " + e.getMessage());
                }
            }
        }
        System.out.println("===========================================\n");
    }

    public String extractListingName(WebElement card) {
        String[] xpaths = {
            ".//h2",
            ".//a[contains(@class,'cardTitle')]",
            ".//p[contains(@class,'lcName')]",
            ".//span[contains(@class,'lcName')]",
            ".//div[contains(@class,'companyName')]",
            ".//p[contains(@class,'p-name')]",
            ".//span[contains(@class,'title')]"
        };
        for (String xpath : xpaths) {
            List<WebElement> found = card.findElements(By.xpath(xpath));
            if (!found.isEmpty()) {
                String text = found.get(0).getText().trim();
                if (!text.isEmpty()) return text;
            }
        }
        return "Name not found";
    }
}