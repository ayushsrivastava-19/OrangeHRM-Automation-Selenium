package com.demoproject.pages;

import com.demoproject.actiondriver.ActionDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    private ActionDriver driver;

    private By usernameField = By.name("username");
    private By passwordField = By.cssSelector("input[type='password']");
    private By loginButton = By.xpath("//button[text()='Login']");
    private By errorMessage = By.xpath("//p[text()='Invalid credentials']");

    public LoginPage(WebDriver dr) {
        this.driver = new ActionDriver(dr);
    }

    public void login(String username, String password) {
        driver.enterText(usernameField, "Admin");
        driver.enterText(passwordField, "admin123");
        driver.click(loginButton);
    }

    public boolean isErrorMessageDisplayed() {
        return driver.isDisplayed(errorMessage);
    }

    public String getErrorMessage() {
        return driver.getText(errorMessage);
    }

    public void verifyErrorMessage(String expectedMessage) {
        driver.compareText(errorMessage, expectedMessage);
    }
}
