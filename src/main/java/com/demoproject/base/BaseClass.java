package com.demoproject.base;

import com.demoproject.actiondriver.ActionDriver;
import com.demoproject.utils.LoggerManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
import org.apache.logging.log4j.Logger;

public class BaseClass {

    protected static Properties prop;
    protected static WebDriver driver;
    private static ActionDriver actionDriver;
    public static final Logger logger = LoggerManager.getLogger(BaseClass.class);

    //Load the configuration file
    @BeforeSuite
    public void configLoad() throws IOException {
        prop = new Properties();
        FileInputStream fis = new FileInputStream("src/main/resources/config.properties");
        prop.load(fis);
        logger.info("config.properties file loaded");
    }

    private void launchBrowser() {
        String browser = prop.getProperty("browser");
        if (browser.equalsIgnoreCase("chrome")) {
            driver = new ChromeDriver();
            logger.info("Chromedriver instance is created");
        } else {
            throw new IllegalArgumentException("Browser is not supported" + browser);
        }
    }

    private void configureBrowser() {
        //Implicit Wait
        int implicitWait = Integer.parseInt(prop.getProperty("implicitWait"));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));

        //maximize the browser
        driver.manage().window().maximize();

        //navigate to url
        try {
            driver.get(prop.getProperty("url"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Properties getProperties() {
        return prop;
    }

    public static WebDriver getWebDriver() {
        if(driver == null){
            System.out.println("WebDriver is not initialized");
            throw new IllegalStateException("WebDriver is not initialized");
        }
        return driver;
    }

    public static ActionDriver getActionDriver() {
        if(actionDriver == null){
            System.out.println("ActionDriver is not initialized");
            throw new IllegalStateException("ActionDriver is not initialized");
        }
        return actionDriver;
    }

    @BeforeMethod
    public void setup() throws IOException {
        System.out.println("Setting up Browser for:" + this.getClass().getSimpleName());
        launchBrowser();
        configureBrowser();
        staticWait(2);
        logger.info("Webdriver initialized and browser maximized");
        logger.trace("This is a trace message");
        logger.error("This is an error message");
        logger.fatal("This is a fatal message");
        logger.warn("This is a warn message");
        logger.debug("This is a debug message");

        if(actionDriver == null){
            actionDriver = new ActionDriver(driver);
            System.out.println("ActionDriver instance is created"+Thread.currentThread().getId());
        }
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            try{
                driver.quit();
            }catch (Exception e){
                System.out.println("unable to quit the driver "+e.getMessage());
            }
        }
        logger.info("WebDriver instance is closed");
        driver = null;
        actionDriver = null;
    }

    //Static wait for pause
    public void staticWait(int seconds){
        LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(seconds));
    }

}
