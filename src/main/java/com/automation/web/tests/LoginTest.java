package com.automation.web.tests;

import com.automation.web.config.DriverManager;
import com.automation.web.data.SauceDemoUsers;
import com.automation.web.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Drives saucedemo.com through Chrome on an Android emulator. Assertions
 * mirror the sibling playwright-web-automation project's login.spec.ts —
 * same site, same expected behavior, different automation tool.
 */
public class LoginTest extends BaseTest {

    @Test(description = "Valid login navigates to the inventory page")
    public void testValidLogin() {
        LoginPage loginPage = new LoginPage();
        loginPage.login(SauceDemoUsers.STANDARD_USERNAME, SauceDemoUsers.PASSWORD);

        String url = DriverManager.getDriver().getCurrentUrl();
        Assert.assertTrue(url.contains("inventory"), "Should navigate to inventory after valid login, got: " + url);
    }

    @Test(description = "Invalid credentials show an error message")
    public void testInvalidLogin() {
        LoginPage loginPage = new LoginPage();
        loginPage.login(SauceDemoUsers.INVALID_USERNAME, SauceDemoUsers.INVALID_PASSWORD);

        Assert.assertTrue(loginPage.isErrorDisplayed(), "Error message should be displayed for invalid credentials");
        Assert.assertTrue(loginPage.getErrorMessage().contains("do not match"),
            "Expected a credentials-mismatch error, got: " + loginPage.getErrorMessage());
    }

    @Test(description = "Locked-out user is rejected with a locked-out message")
    public void testLockedOutUser() {
        LoginPage loginPage = new LoginPage();
        loginPage.login(SauceDemoUsers.LOCKED_OUT_USERNAME, SauceDemoUsers.PASSWORD);

        Assert.assertTrue(loginPage.isErrorDisplayed(), "Locked-out error message should be displayed");
        Assert.assertTrue(loginPage.getErrorMessage().contains("locked out"),
            "Expected a locked-out error, got: " + loginPage.getErrorMessage());
    }

    @Test(description = "Empty username shows a validation error")
    public void testEmptyUsername() {
        LoginPage loginPage = new LoginPage();
        loginPage.login("", SauceDemoUsers.PASSWORD);

        Assert.assertTrue(loginPage.isErrorDisplayed(), "Error message should appear when username is empty");
        Assert.assertTrue(loginPage.getErrorMessage().contains("Username is required"),
            "Expected a username-required error, got: " + loginPage.getErrorMessage());
    }

    @Test(description = "Empty password shows a validation error")
    public void testEmptyPassword() {
        LoginPage loginPage = new LoginPage();
        loginPage.login(SauceDemoUsers.STANDARD_USERNAME, "");

        Assert.assertTrue(loginPage.isErrorDisplayed(), "Error message should appear when password is empty");
        Assert.assertTrue(loginPage.getErrorMessage().contains("Password is required"),
            "Expected a password-required error, got: " + loginPage.getErrorMessage());
    }
}
