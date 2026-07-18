package com.automation.tests;

import com.automation.data.TestUsers;
import com.automation.pages.LoginPage;
import com.automation.pages.NavigationPage;
import com.automation.pages.ProductCatalogPage;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * The app has no login wall on launch — it lands on the product catalog
 * either way, and Login/Logout lives inside the hamburger menu. Every test
 * here opens that menu first, matching how the app is actually navigated
 * (verified against the app's own instrumented LoginTest.java).
 */
public class LoginTest extends BaseTest {

    @Test(description = "Valid login should navigate back to the product catalog")
    public void testValidLogin() {
        NavigationPage navigationPage = new NavigationPage();
        navigationPage.goToLogin();

        LoginPage loginPage = new LoginPage();
        loginPage.login(TestUsers.STANDARD_USERNAME, TestUsers.PASSWORD);

        ProductCatalogPage productCatalogPage = new ProductCatalogPage();
        Assert.assertTrue(productCatalogPage.isDisplayed(), "Should land back on the product catalog after login");
        Assert.assertTrue(navigationPage.isLogoutMenuItemDisplayed(), "Menu should now offer Logout instead of Login");
    }

    @Test(description = "Locked-out account should be rejected with a locked-out message")
    public void testLockedOutUser() {
        NavigationPage navigationPage = new NavigationPage();
        navigationPage.goToLogin();

        LoginPage loginPage = new LoginPage();
        loginPage.login(TestUsers.LOCKED_OUT_USERNAME, TestUsers.PASSWORD);

        Assert.assertTrue(loginPage.isPasswordErrorDisplayed(), "Locked-out message should be displayed");
        Assert.assertEquals(loginPage.getPasswordError(), "Sorry this user has been locked out.");
    }

    @Test(description = "Empty username should show a username-required validation error")
    public void testEmptyUsername() {
        NavigationPage navigationPage = new NavigationPage();
        navigationPage.goToLogin();

        LoginPage loginPage = new LoginPage();
        loginPage.login("", TestUsers.PASSWORD);

        Assert.assertTrue(loginPage.isUsernameErrorDisplayed(), "Username required error should appear");
        Assert.assertEquals(loginPage.getUsernameError(), "Username is required");
    }

    @Test(description = "Empty password should show a password-required validation error")
    public void testEmptyPassword() {
        NavigationPage navigationPage = new NavigationPage();
        navigationPage.goToLogin();

        LoginPage loginPage = new LoginPage();
        loginPage.login(TestUsers.STANDARD_USERNAME, "");

        Assert.assertTrue(loginPage.isPasswordErrorDisplayed(), "Password required error should appear");
        Assert.assertEquals(loginPage.getPasswordError(), "Enter Password");
    }
}
