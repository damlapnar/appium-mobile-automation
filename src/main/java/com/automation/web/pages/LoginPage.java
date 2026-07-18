package com.automation.web.pages;

import org.openqa.selenium.By;

/**
 * saucedemo.com's login page — same selectors as the sibling
 * playwright-web-automation project's LoginPage.ts, since it's the same
 * site's DOM either way.
 */
public class LoginPage extends BasePage {

    private static final By USERNAME = By.cssSelector("[data-test='username']");
    private static final By PASSWORD = By.cssSelector("[data-test='password']");
    private static final By LOGIN_BUTTON = By.cssSelector("[data-test='login-button']");
    private static final By ERROR_MESSAGE = By.cssSelector("[data-test='error']");

    public void enterUsername(String username) {
        type(USERNAME, username);
    }

    public void enterPassword(String password) {
        type(PASSWORD, password);
    }

    public void tapLogin() {
        tap(LOGIN_BUTTON);
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        tapLogin();
    }

    public String getErrorMessage() {
        return getText(ERROR_MESSAGE);
    }

    public boolean isErrorDisplayed() {
        return isDisplayed(ERROR_MESSAGE);
    }
}
