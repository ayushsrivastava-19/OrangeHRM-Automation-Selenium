package com.demoproject.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.demoproject.base.BaseClass;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class ExtentManager {
    private static ExtentReports extentReports;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    private static Map<Long, WebDriver> driverMap = new ConcurrentHashMap<>();

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

            Properties prop = BaseClass.getProperties();
            if (prop != null && prop.getProperty("environment") != null) {
                extentReports.setSystemInfo("Environment", prop.getProperty("environment"));
            }
            if (prop != null && prop.getProperty("browser") != null) {
                extentReports.setSystemInfo("Browser", prop.getProperty("browser"));
            }
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
        driverMap.put(Thread.currentThread().threadId(), driver);
    }

    public static WebDriver getRegisteredWebDriver(){
        return driverMap.get(Thread.currentThread().threadId());
    }

    public static void unregisterWebDriver(){
        driverMap.remove(Thread.currentThread().threadId());
    }

    public static String captureScreenshot(String testName){
        WebDriver driver = getRegisteredWebDriver();
        if (driver == null) {
            return null;
        }
        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String path = System.getProperty("user.dir")
                    + "/target/ExtentReport/screenshots/"
                    + testName + "_" + System.currentTimeMillis() + ".png";
            File dest = new File(path);
            dest.getParentFile().mkdirs();
            FileUtils.copyFile(src, dest);
            return path;
        } catch (IOException e) {
            return null;
        }
    }
}
