package com.orangehrmproject.demo;

import com.demoproject.base.BaseClass;
import com.demoproject.pages.HomePage;
import com.demoproject.pages.LoginPage;
import com.orangehrmproject.demo.dataprovider.ExcelDataProvider;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginPageTest extends BaseClass {

    private LoginPage loginPage;
    private HomePage homePage;

    @BeforeMethod
    public void setupPages() {
        loginPage = new LoginPage(getWebDriver());
        homePage = new HomePage(getWebDriver());
    }

    @Test(dataProvider = "loginData", dataProviderClass = ExcelDataProvider.class)
    public void verifyLoginWithExcelData(String username, String password, String expectedResult) {
        loginPage.login(username, password);

        if ("valid".equalsIgnoreCase(expectedResult)) {
            Assert.assertTrue(homePage.isAdminTabVisible(),
                    "Admin tab should be visible after successful login for user: " + username);
            homePage.logout();
            staticWait(2);
        } else {
            Assert.assertTrue(loginPage.verifyErrorMessage("Invalid credentials"),
                    "Invalid credentials message should be shown for user: " + username);
        }
    }
}
