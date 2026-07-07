package com.automation.tests;

import com.automation.config.DriverManager;
import com.automation.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test(description = "Valid login navigates to inventory page")
    public void testValidLogin() {
        LoginPage loginPage = new LoginPage();
        loginPage.login("standard_user", "secret_sauce");
        String url = DriverManager.getDriver().getCurrentUrl();
        Assert.assertTrue(url.contains("inventory"),
            "Should navigate to inventory after valid login, got: " + url);
    }

    @Test(description = "Invalid credentials show error message")
    public void testInvalidLogin() {
        LoginPage loginPage = new LoginPage();
        loginPage.login("invalid_user", "wrong_password");
        Assert.assertTrue(loginPage.isErrorDisplayed(),
            "Error message should be displayed for invalid credentials");
    }

    @Test(description = "Empty username shows validation error")
    public void testEmptyUsername() {
        LoginPage loginPage = new LoginPage();
        loginPage.login("", "secret_sauce");
        Assert.assertTrue(loginPage.isErrorDisplayed(),
            "Error message should appear when username is empty");
    }

    @Test(description = "Empty password shows validation error")
    public void testEmptyPassword() {
        LoginPage loginPage = new LoginPage();
        loginPage.login("standard_user", "");
        Assert.assertTrue(loginPage.isErrorDisplayed(),
            "Error message should appear when password is empty");
    }
}
