package org.example.utils;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.example.tests.TC03_FreeListingFormTest;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ExtentTestListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test = ExtentReportManager.getInstance()
                .createTest(result.getMethod().getMethodName());
        ExtentReportManager.setTest(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentReportManager.getTest().log(Status.PASS, "Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentReportManager.getTest().log(Status.FAIL, result.getThrowable());

        // Capture screenshot on failure
        String screenshotPath = ScreenshotUtil.capture(
                TC03_FreeListingFormTest.getDriver(),
                result.getMethod().getMethodName());
        ExtentReportManager.getTest().addScreenCaptureFromPath(screenshotPath);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentReportManager.getTest().log(Status.SKIP, "Test Skipped");
    }

    @Override
    public void onFinish(org.testng.ITestContext context) {
        ExtentReportManager.flush();
    }
}