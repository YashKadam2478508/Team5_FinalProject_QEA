package org.example.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager {

    private static ExtentReports extent;
    private static final ThreadLocal<ExtentTest> testThread = new ThreadLocal<>();

    public static ExtentReports getInstance() {
        if (extent == null) {
            ExtentSparkReporter spark = new ExtentSparkReporter("reports/ExtentReport.html");
            spark.config().setTheme(Theme.DARK);
            spark.config().setDocumentTitle("IndiaMart Automation Report");
            spark.config().setReportName("Team 5 - Sprint 2 Results");

            extent = new ExtentReports();
            extent.attachReporter(spark);
            extent.setSystemInfo("Project",  "Team5 Final Project");
            extent.setSystemInfo("Browser",  "Chrome");
            extent.setSystemInfo("Sprint",   "Sprint 2 - Free Listing");
        }
        return extent;
    }

    public static ExtentTest getTest()                    { return testThread.get(); }
    public static void        setTest(ExtentTest test)    { testThread.set(test); }
    public static void        flush()                     { if (extent != null) extent.flush(); }
}