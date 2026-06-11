package com.demoproject.pages;

import com.demoproject.actiondriver.ActionDriver;
import com.demoproject.base.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {

    private ActionDriver driver;

    private By adminTab = By.xpath("//span[text()='Admin']");
    private By userIDButton = By.className("oxd-userdropdown-name");
    private By logoutButton = By.xpath("//a[text()='Logout']");
    private By orangeHRMLogo =  By.xpath("//div[@class='oxd-brand-banner']//img");

    public HomePage(WebDriver webDriver){
        this.driver = BaseClass.getActionDriver();
    }

    public boolean isAdminTabVisible(){
        return driver.isDisplayed(adminTab);
    }

    public boolean verifyOrangeHRMLogo(){
        return driver.isDisplayed(orangeHRMLogo);
    }

    public void logout(){
        driver.click(userIDButton);
        driver.click(logoutButton);
    }
}
