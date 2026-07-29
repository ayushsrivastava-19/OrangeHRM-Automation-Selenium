package com.demoproject.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.devtools.v141.systeminfo.SystemInfo;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ExtentManager {
    private static ExtentReports extentReports;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    private static Map<Long, WebDriver> driverMap = new HashMap<>();

    public static synchronized ExtentReports getExtentReports(){
        if(extentReports == null){
            String reportPath = System.getProperty("user.dir")+"/target/ExtentReport/ExtentReport.html";
            new File(reportPath).getParentFile().mkdirs();
            ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
            spark.config().setReportName("Automation Test Report");
            spark.config().setDocumentTitle("OrangeHRM report");
            spark.config().setTheme(Theme.DARK);
            extentReports = new ExtentReports();
            extentReports.attachReporter(spark);
            extentReports.setSystemInfo("Operating System", System.getProperty("os.name"));
            extentReports.setSystemInfo("Java version", System.getProperty("java.version"));
            extentReports.setSystemInfo("User name", System.getProperty("user.name"));
        }
        return extentReports;
    }

    /*
       Start the test
     */
    public static ExtentTest startTest(String testName){
        ExtentTest extentTest = getExtentReports().createTest(testName);
        test.set(extentTest);
        return extentTest;
    }

    public static ExtentTest getTest(){
        return test.get();
    }

    public static void removeTest(){
        test.remove();
    }
    /*
      End the test
     */
    public static void endTest(){
        getExtentReports().flush();
    }

    public static void registerWebDriver(WebDriver driver){
        driverMap.put(Thread.currentThread().getId(), driver);
    }


}
