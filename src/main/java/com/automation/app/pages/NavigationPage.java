package com.automation.app.pages;

import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/**
 * The hamburger menu / side navigation drawer present on the app's main
 * screens. The app has no login wall on launch — Login/Logout lives inside
 * this drawer, so every login-related flow starts here.
 */
public class NavigationPage extends BasePage {

    @AndroidFindBy(id = "menuIV")
    private WebElement menuButton;

    @AndroidFindBy(accessibility = "Login Menu Item")
    private WebElement loginMenuItem;

    @AndroidFindBy(accessibility = "Logout Menu Item")
    private WebElement logoutMenuItem;

    // Logout shows a native AlertDialog ("Are you sure you want to logout")
    // before actually logging out — confirmed in MainActivity.java's
    // showLogoutAlertDialog(). android:id/button1 is the Android framework's
    // own resource id for an AlertDialog's positive button, not an app id.
    @AndroidFindBy(id = "android:id/button1")
    private WebElement logoutConfirmButton;

    public void openMenu() {
        tap(menuButton);
    }

    public void goToLogin() {
        openMenu();
        tap(loginMenuItem);
    }

    public void logout() {
        openMenu();
        tap(logoutMenuItem);
        tap(logoutConfirmButton);
    }

    public boolean isLoginMenuItemDisplayed() {
        return isDisplayed(loginMenuItem);
    }

    public boolean isLogoutMenuItemDisplayed() {
        return isDisplayed(logoutMenuItem);
    }
}
