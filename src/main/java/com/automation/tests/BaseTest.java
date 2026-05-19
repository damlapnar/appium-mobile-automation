package com.automation.tests;

import com.automation.config.DriverManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

public class BaseTest {

    @BeforeMethod
    @Parameters({"platform"})
    public void setUp(String platform) throws Exception {
        if ("android".equalsIgnoreCase(platform)) {
            DriverManager.initAndroidDriver();
        } else if ("ios".equalsIgnoreCase(platform)) {
            DriverManager.initIOSDriver();
        } else {
            throw new IllegalArgumentException("Platform must be 'android' or 'ios'");
        }
    }

    @AfterMethod
    public void tearDown() {
        DriverManager.quitDriver();
    }
}
