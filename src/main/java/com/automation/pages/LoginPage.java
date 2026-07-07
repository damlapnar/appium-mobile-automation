package com.automation.pages;

import org.openqa.selenium.By;

public class LoginPage extends BasePage {

    private static final By USERNAME  = By.cssSelector("[data-test='username']");
    private static final By PASSWORD  = By.cssSelector("[data-test='password']");
    private static final By BTN_LOGIN = By.cssSelector("[data-test='login-button']");
    private static final By ERROR_MSG = By.cssSelector("[data-test='error']");

    public void enterUsername(String username) {
        type(USERNAME, username);
    }

    public void enterPassword(String password) {
        type(PASSWORD, password);
    }

    public void tapLogin() {
        tap(BTN_LOGIN);
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        tapLogin();
    }

    public String getErrorMessage() {
        return getText(ERROR_MSG);
    }

    public boolean isErrorDisplayed() {
        return isDisplayed(ERROR_MSG);
    }
}
