package com.automation.pages;

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

    public void openMenu() {
        tap(menuButton);
    }

    public void goToLogin() {
        openMenu();
        tap(loginMenuItem);
    }

    public void goToLogout() {
        openMenu();
        tap(logoutMenuItem);
    }

    public boolean isLoginMenuItemDisplayed() {
        return isDisplayed(loginMenuItem);
    }

    public boolean isLogoutMenuItemDisplayed() {
        return isDisplayed(logoutMenuItem);
    }
}
