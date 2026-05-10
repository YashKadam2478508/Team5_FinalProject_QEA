package org.example.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.utils.ExcelUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

public class IndiaMartResultsPage extends BasePage {

    private static final Logger log = LogManager.getLogger(IndiaMartResultsPage.class);

    private static final String[] LISTING_HEADERS = {"#", "Service Name", "Company Name", "Rating", "Location"};
    private static final int      MAX_RECORDS     = 5;

    public IndiaMartResultsPage(WebDriver driver) {
        super(driver);
    }

    public void handlePostSearchPopup() {
        log.info("Checking for post-search popup.");
        waitAndHandlePopup();
        log.info("Post-search popup check complete.");
    }

    public void applyChennaiFilter() {
        log.info("Applying Chennai-based Suppliers filter.");
        handlePopupIfVisible();
        WebElement checkbox = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//input[@id='Chennai-based Suppliersid']")));
        safeClick(checkbox);
        wait.until(d -> js.executeScript("return document.readyState").equals("complete"));
        log.info("Chennai-based Suppliers filter applied.");
    }

    public void applyFoamWashFilter() {
        log.info("Applying Foam Wash filter.");
        handlePopupIfVisible();
        WebElement foamWash = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//span[normalize-space()='Foam Wash']")));
        safeClick(foamWash);
        wait.until(d -> js.executeScript("return document.readyState").equals("complete"));
        log.info("Foam Wash filter applied.");
    }

    public void applyVacuumingFilter() {
        log.info("Applying Vacuuming filter.");
        handlePopupIfVisible();
        WebElement vacuuming = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//span[normalize-space()='Vacuuming']")));
        safeClick(vacuuming);
        wait.until(d -> js.executeScript("return document.readyState").equals("complete"));
        log.info("Vacuuming filter applied.");
    }

    public void applyOnsiteFilter() {
        log.info("Applying On-site filter.");
        handlePopupIfVisible();
        WebElement onsite = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//span[normalize-space()='On-site']")));
        safeClick(onsite);
        wait.until(d -> js.executeScript("return document.readyState").equals("complete"));
        log.info("On-site filter applied.");
    }

    public void applyGymOnlyFilter() {
        log.info("Applying Gym Only filter.");
        handlePopupIfVisible();
        WebElement gymOnly = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//span[normalize-space()='Gym Only']")));
        safeClick(gymOnly);
        wait.until(d -> js.executeScript("return document.readyState").equals("complete"));
        log.info("Gym Only filter applied.");
    }


    public void displayTop5Listings() {
        List<WebElement> cards = driver.findElements(By.cssSelector(".card.brs5"));
        List<String[]>   excelRows = new ArrayList<>();

        log.info("===== CAR WASHING SERVICES IN CHENNAI (TOP {}) =====", MAX_RECORDS);

        if (cards.isEmpty()) {
            log.warn("No results found. URL: {}", driver.getCurrentUrl());
        } else {
            int count = Math.min(MAX_RECORDS, cards.size());
            log.info("Displaying {} of {} result(s) found.", count, cards.size());

            for (int i = 0; i < count; i++) {
                try {
                    String serviceName = extractText(cards.get(i), ".producttitle a.cardlinks");
                    String companyName = extractText(cards.get(i), ".companyname a.cardlinks");
                    String rating      = extractAttr(cards.get(i), "data-rating");
                    String location    = extractText(cards.get(i), ".highlight");

                    log.info("{}. Service: {} | Company: {} | Rating: {} | Location: {}",
                            i + 1, serviceName, companyName, rating, location);

                    excelRows.add(new String[]{
                            String.valueOf(i + 1), serviceName, companyName, rating, location
                    });

                } catch (Exception e) {
                    log.error("Error reading card {}: {}", i + 1, e.getMessage());
                }
            }
        }

        log.info("====================================================");
        ExcelUtil.writeListings(
                "test-output/CarWashServices.xlsx",
                "Car Wash Services",
                LISTING_HEADERS,
                excelRows
        );
    }

    public void displayAllListings() {
        List<WebElement> cards = driver.findElements(By.cssSelector(".card.brs5"));
        List<String[]>   excelRows = new ArrayList<>();

        log.info("===== FITNESS CENTERS IN CHENNAI - GYM ONLY (TOP {}) =====", MAX_RECORDS);

        if (cards.isEmpty()) {
            log.warn("No results found. URL: {}", driver.getCurrentUrl());
        } else {
            int count = Math.min(MAX_RECORDS, cards.size());
            log.info("Displaying {} of {} result(s) found.", count, cards.size());

            for (int i = 0; i < count; i++) {
                try {
                    String serviceName = extractText(cards.get(i), ".producttitle a.cardlinks");
                    String companyName = extractText(cards.get(i), ".companyname a.cardlinks");
                    String rating      = extractAttr(cards.get(i), "data-rating");
                    String location    = extractText(cards.get(i), ".highlight");

                    log.info("{}. Service: {} | Company: {} | Rating: {} | Location: {}",
                            i + 1, serviceName, companyName, rating, location);

                    excelRows.add(new String[]{
                            String.valueOf(i + 1), serviceName, companyName, rating, location
                    });

                } catch (Exception e) {
                    log.error("Error reading card {}: {}", i + 1, e.getMessage());
                }
            }
        }

        log.info("==========================================================");
        ExcelUtil.writeListings(
                "test-output/FitnessCenter.xlsx",
                "Fitness Centers",
                LISTING_HEADERS,
                excelRows
        );
    }


    private String extractText(WebElement card, String cssSelector) {
        try {
            String text = card.findElement(By.cssSelector(cssSelector)).getText().trim();
            return text.isEmpty() ? "N/A" : text;
        } catch (Exception e) {
            return "N/A";
        }
    }

    private String extractAttr(WebElement card, String attribute) {
        try {
            String val = card.getAttribute(attribute);
            return (val == null || val.isEmpty()) ? "N/A" : val;
        } catch (Exception e) {
            return "N/A";
        }
    }
}
