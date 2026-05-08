package org.example.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class BasePage {

    private static final Logger log = LogManager.getLogger(BasePage.class);

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected JavascriptExecutor js;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.js     = (JavascriptExecutor) driver;
    }

    // Waits up to 5s for popup to appear, then closes it
    protected void waitAndHandlePopup() {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement closeButton = shortWait.until(
                    ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='idfpclose']")));
            try {
                closeButton.click();
            } catch (ElementClickInterceptedException e) {
                js.executeScript("arguments[0].click();", closeButton);
            }
            log.warn("Popup detected and closed.");
        } catch (TimeoutException e) {
            log.info("Popup did not appear within 5s — skipping.");
        } catch (Exception e) {
            log.warn("Popup check encountered an error — skipping. Reason: {}", e.getMessage());
        }
    }

    // Instant check — closes popup only if already visible right now
    protected void handlePopupIfVisible() {
        try {
            List<WebElement> popupContainer = driver.findElements(
                    By.xpath("//div[@id='identyfy_usr_ctl']"));
            if (!popupContainer.isEmpty() && popupContainer.get(0).isDisplayed()) {
                List<WebElement> closeButton = driver.findElements(
                        By.xpath("//a[@id='idfpclose']"));
                if (!closeButton.isEmpty()) {
                    try {
                        closeButton.get(0).click();
                    } catch (ElementClickInterceptedException e) {
                        js.executeScript("arguments[0].click();", closeButton.get(0));
                    }
                    log.warn("Popup was visible — detected and closed.");
                }
            }
        } catch (Exception e) { /* popup not present, continue */ }
    }

    // Scrolls to element + clicks, retries if popup intercepts
    protected void safeClick(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
        int attempts = 0;
        while (attempts < 3) {
            try {
                element.click();
                return;
            } catch (ElementClickInterceptedException e) {
                log.warn("Click intercepted — dismissing popup and retrying (attempt {}).", attempts + 1);
                handlePopupIfVisible();
                attempts++;
            }
        }
        log.warn("Standard click failed after 3 attempts — using JS click as fallback.");
        js.executeScript("arguments[0].click();", element);
    }

    // Waits up to 5s for t0901 popup (secondary popup) and closes it
    protected boolean handleT0901PopupIfAppears() {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement popup = shortWait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//div[@id='t0901_cls']")));
            try {
                popup.click();
            } catch (ElementClickInterceptedException e) {
                js.executeScript("arguments[0].click();", popup);
            }
            log.warn("t0901 popup detected and closed.");
            return true;
        } catch (TimeoutException e) {
            log.info("t0901 popup did not appear — proceeding.");
            return false;
        } catch (Exception e) {
            log.warn("t0901 popup check error — skipping. Reason: {}", e.getMessage());
            return false;
        }
    }
}
