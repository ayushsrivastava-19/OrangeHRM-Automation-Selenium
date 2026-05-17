package com.demoproject.actiondriver;

import com.demoproject.base.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class ActionDriver {

    private WebDriver driver;
    private WebDriverWait wait;

    public ActionDriver(WebDriver driver) {
        this.driver = driver;
        int sec = Integer.parseInt(BaseClass.getProperties().getProperty("explicitWait"));
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(sec));
    }

    public void click(By locator)
    {
        try{
            waitForElementToBeClickable(locator);
            driver.findElement(locator).click();
        }catch (Exception e){
            System.out.println("Unable to click element " + e.getMessage());
        }
    }

    public void enterText(By by, String text){
        try{
            waitForElementToBeVisible(by);
            driver.findElement(by).clear();
            driver.findElement(by).sendKeys(text);
        }
        catch (Exception e){
            System.out.println("Unable to enter text " + e.getMessage());
        }
    }

    public String getText(By by){
        try{
            waitForElementToBeVisible(by);
            return driver.findElement(by).getText();
        }catch (Exception e){
            System.out.println("Unable to get text " + e.getMessage());
        }
        return "";
    }

    public void compareText(By by, String text){
        try{
            waitForElementToBeVisible(by);
            String actualText = driver.findElement(by).getText();
            if(text.equals(actualText)){
                System.out.println("Successfully compare text " + actualText);
            }
            else{
                System.out.println("Failed compare text " + actualText);
            }
        }catch (Exception e){
            System.out.println("Unable to compare text " + e.getMessage());
        }
    }

    public boolean isDisplayed(By by){
        try{
            waitForElementToBeVisible(by);
            boolean displayed = driver.findElement(by).isDisplayed();
            if(displayed){
                System.out.println("Element is visible");
                return displayed;
            }
            else {
                return displayed;
            }
        }
        catch (Exception e){
            System.out.println("Unable to display element " + e.getMessage());
        }
        return false;
    }

    public void scrollToElement(By by){
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(by));
    }

    private void waitForElementToBeClickable(By by){
        try {
            wait.until(ExpectedConditions.elementToBeClickable(by));
        }
        catch(Exception e) {
            System.out.println("Element is not clickable"+e.getMessage());
        }
    }

    private void waitForElementToBeVisible(By by){
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        }catch (Exception e) {
            System.out.println("Element is not visible"+e.getMessage());
        }
    }
}
