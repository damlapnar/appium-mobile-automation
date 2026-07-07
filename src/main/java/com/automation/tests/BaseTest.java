package com.automation.tests;

import com.automation.config.DriverManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

public class BaseTest {

    protected static final String BASE_URL =
        System.getProperty("baseUrl", "https://www.saucedemo.com");

    @BeforeMethod
    @Parameters({"platform"})
    public void setUp(String platform) throws Exception {
        if ("android".equalsIgnoreCase(platform)) {
            DriverManager.initAndroidDriver();
        } else {
            throw new IllegalArgumentException("Unsupported platform: " + platform);
        }
        DriverManager.getDriver().get(BASE_URL);
    }

    @AfterMethod
    public void tearDown() {
        DriverManager.quitDriver();
    }
}
