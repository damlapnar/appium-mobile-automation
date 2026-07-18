package com.automation.web.tests;

import com.automation.web.config.DriverManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

public class BaseTest {

    protected static final String BASE_URL = System.getProperty("baseUrl", "https://www.saucedemo.com");

    @BeforeMethod
    @Parameters({"platform"})
    public void setUp(String platform) throws Exception {
        if (!"android".equalsIgnoreCase(platform)) {
            throw new IllegalArgumentException("Mobile web suite only supports 'android' (Chrome), got: " + platform);
        }
        DriverManager.initAndroidDriver();
        DriverManager.getDriver().get(BASE_URL);
    }

    @AfterMethod
    public void tearDown() {
        DriverManager.quitDriver();
    }
}
