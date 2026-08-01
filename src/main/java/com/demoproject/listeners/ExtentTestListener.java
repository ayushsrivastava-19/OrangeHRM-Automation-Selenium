package com.demoproject.listeners;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.demoproject.utils.ExtentManager;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ExtentTestListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        ExtentManager.startTest(result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentManager.getTest().pass("Test passed");
        ExtentManager.removeTest();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String screenshotPath = ExtentManager.captureScreenshot(result.getMethod().getMethodName());
        if (screenshotPath != null) {
            try {
                ExtentManager.getTest().fail(result.getThrowable(),
                        MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
            } catch (Exception e) {
                ExtentManager.getTest().fail(result.getThrowable());
                ExtentManager.getTest().warning("Screenshot capture failed: " + e.getMessage());
            }
        } else {
            ExtentManager.getTest().fail(result.getThrowable());
            ExtentManager.getTest().warning("Screenshot not available - WebDriver was not registered");
        }
        ExtentManager.removeTest();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        if (result.getThrowable() != null) {
            ExtentManager.getTest().skip(result.getThrowable());
        } else {
            ExtentManager.getTest().skip("Test skipped");
        }
        ExtentManager.removeTest();
    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentManager.endTest();
    }
}
