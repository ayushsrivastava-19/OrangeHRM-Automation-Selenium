package com.orangehrmproject.demo;

import com.demoproject.base.BaseClass;
import com.demoproject.pages.HomePage;
import com.demoproject.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class HomePageTest extends BaseClass {

    private HomePage homePage;
    private LoginPage loginPage;

    @BeforeMethod
    public void setupPages(){
        loginPage = new LoginPage(getDriver());
        homePage = new HomePage(getDriver());
    }

    @Test
    public void verifyOrangeHRMLogo(){
        loginPage.login("Admin","admin123");
        Assert.assertTrue(homePage.verifyOrangeHRMLogo(), "Logo is not visible");
    }
}
