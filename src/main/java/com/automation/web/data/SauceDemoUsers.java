package com.automation.web.data;

/**
 * saucedemo.com's built-in accounts, all sharing one password. Same site
 * (and same accounts) the sibling playwright-web-automation project tests
 * directly in a browser — here it's driven through Chrome on an Android
 * emulator via Appium instead.
 */
public final class SauceDemoUsers {

    public static final String PASSWORD = "secret_sauce";

    public static final String STANDARD_USERNAME = "standard_user";
    public static final String LOCKED_OUT_USERNAME = "locked_out_user";
    public static final String INVALID_USERNAME = "invalid_user";
    public static final String INVALID_PASSWORD = "wrong_password";

    private SauceDemoUsers() {
    }
}
