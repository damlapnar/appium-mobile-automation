package com.automation.tests;

import com.automation.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test(description = "Valid login should navigate to home screen")
    public void testValidLogin() {
        LoginPage loginPage = new LoginPage();
        loginPage.login("standard_user", "secret_sauce");
        // Assert navigation to home — add HomePage POM for full assertion
    }

    @Test(description = "Invalid credentials should show error message")
    public void testInvalidLogin() {
        LoginPage loginPage = new LoginPage();
        loginPage.login("invalid_user", "wrong_password");

        Assert.assertTrue(loginPage.isErrorDisplayed(), "Error message should be displayed");
    }

    @Test(description = "Empty username should show validation error")
    public void testEmptyUsername() {
        LoginPage loginPage = new LoginPage();
        loginPage.login("", "secret_sauce");

        Assert.assertTrue(loginPage.isErrorDisplayed(), "Username required error should appear");
    }

    @Test(description = "Empty password should show validation error")
    public void testEmptyPassword() {
        LoginPage loginPage = new LoginPage();
        loginPage.login("standard_user", "");

        Assert.assertTrue(loginPage.isErrorDisplayed(), "Password required error should appear");
    }
}
