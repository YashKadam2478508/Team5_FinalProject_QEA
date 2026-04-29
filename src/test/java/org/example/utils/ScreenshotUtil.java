package org.example.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Captures screenshots to reports/screenshots/ folder.
 */
public class ScreenshotUtil {

    private static final String SCREENSHOT_DIR = "reports/screenshots/";

    private ScreenshotUtil() { /* utility class */ }

    public static String capture(WebDriver driver, String testName) {
        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String fileName  = testName + "_" + timestamp + ".png";
        String filePath  = SCREENSHOT_DIR + fileName;

        try {
            File src  = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File dest = new File(filePath);
            dest.getParentFile().mkdirs();
            Files.copy(src.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Screenshot saved: " + filePath);
        } catch (IOException e) {
            System.err.println("Screenshot capture failed: " + e.getMessage());
        }
        return filePath;
    }
}
