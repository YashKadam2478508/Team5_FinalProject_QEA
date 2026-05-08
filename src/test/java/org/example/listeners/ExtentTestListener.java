package org.example.listeners;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.base.BaseTest;
import org.example.utils.ScreenshotUtil;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ExtentTestListener implements ITestListener {

    private static final Logger log = LogManager.getLogger(ExtentTestListener.class);

    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        log.info("TEST STARTED: {}", testName);
        ExtentTest test = ExtentReportManager.getInstance().createTest(testName);
        ExtentReportManager.setTest(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        log.info("TEST PASSED:  {}", testName);
        ExtentReportManager.getTest().log(Status.PASS, "Test Passed");

        String screenshotPath = ScreenshotUtil.capture(BaseTest.getDriver(), testName);
        try {
            ExtentReportManager.getTest()
                    .pass("Screenshot of passed test")
                    .addScreenCaptureFromPath(screenshotPath);
        } catch (Exception e) {
            log.warn("Could not attach success screenshot: {}", e.getMessage());
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        log.error("TEST FAILED:  {} — {}", testName, result.getThrowable().getMessage());
        ExtentReportManager.getTest().log(Status.FAIL, result.getThrowable());

        String screenshotPath = ScreenshotUtil.capture(BaseTest.getDriver(), testName);
        try {
            ExtentReportManager.getTest()
                    .fail("Screenshot of failed test")
                    .addScreenCaptureFromPath(screenshotPath);
        } catch (Exception e) {
            log.warn("Could not attach failure screenshot: {}", e.getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        log.warn("TEST SKIPPED: {}", result.getMethod().getMethodName());
        ExtentReportManager.getTest().log(Status.SKIP, "Test Skipped");
    }

    @Override
    public void onFinish(org.testng.ITestContext context) {
        log.info("All tests finished. Flushing ExtentReport.");
        ExtentReportManager.flush();
    }
}
