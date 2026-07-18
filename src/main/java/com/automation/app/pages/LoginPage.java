package com.automation.app.pages;

import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/**
 * Login screen of Sauce Labs' "My Demo App" (see apps/README.md). Locators
 * are resource-ids verified against the app's public layout XML
 * (fragment_login.xml) — the username/password fields have no
 * content-description, so accessibility-id locators wouldn't find them.
 *
 * The app validates one field at a time: submitting with an empty username
 * always reports "Username is required" regardless of the password field,
 * and only once a username is present does an empty password get its own
 * "Enter Password" error. There is no single shared error element.
 */
public class LoginPage extends BasePage {

    @AndroidFindBy(id = "nameET")
    private WebElement usernameField;

    @AndroidFindBy(id = "passwordET")
    private WebElement passwordField;

    @AndroidFindBy(id = "loginBtn")
    private WebElement loginButton;

    @AndroidFindBy(id = "nameErrorTV")
    private WebElement usernameError;

    @AndroidFindBy(id = "passwordErrorTV")
    private WebElement passwordError;

    public void enterUsername(String username) {
        type(usernameField, username);
    }

    public void enterPassword(String password) {
        type(passwordField, password);
    }

    public void tapLogin() {
        tap(loginButton);
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        tapLogin();
    }

    public String getUsernameError() {
        return getText(usernameError);
    }

    public String getPasswordError() {
        return getText(passwordError);
    }

    public boolean isUsernameErrorDisplayed() {
        return isDisplayed(usernameError);
    }

    public boolean isPasswordErrorDisplayed() {
        return isDisplayed(passwordError);
    }
}
