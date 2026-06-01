package com.orangehrmproject.demo;

import com.beust.ah.A;
import com.demoproject.base.BaseClass;
import com.demoproject.pages.HomePage;
import com.demoproject.pages.LoginPage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginPageTest extends BaseClass {

    private LoginPage loginPage;
    private HomePage homePage;

    @BeforeMethod
    public void setupPages(){
        loginPage = new LoginPage(getDriver());
        homePage = new HomePage(getDriver());
    }

    @Test
    public void verifyValidLoginTest(){
        loginPage.login("Admin", "admin123");
        Assert.assertTrue(homePage.isAdminTabVisible(),"Admin tab should be visible after successfull login");
        homePage.logout();
        staticWait(2);
    }

    @Test
    public void verifyInvalidLoginTest(){
        loginPage.login("ad","admin");
        String expectedErrorMessage = "Invalid credentials";
        Assert.assertTrue();
    }
}
