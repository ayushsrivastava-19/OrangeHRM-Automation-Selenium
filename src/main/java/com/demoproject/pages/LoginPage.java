package com.demoproject.pages;

import com.demoproject.actiondriver.ActionDriver;
import com.demoproject.base.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    private ActionDriver driver;

    private By usernameField = By.name("username");
    private By passwordField = By.cssSelector("input[type='password']");
    private By loginButton = By.xpath("//button[normalize-space()='Login']");
    private By errorMessage = By.xpath("//p[text()='Invalid credentials']");

    public LoginPage(WebDriver webDriver) {
        this.driver = BaseClass.getActionDriver();
    }

    public void login(String username, String password) {
        driver.enterText(usernameField, username);
        driver.enterText(passwordField, password);
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
