package com.automation.config;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.Duration;

public class DriverManager {

    private static final ThreadLocal<AppiumDriver> driverThread = new ThreadLocal<>();

    public static AppiumDriver getDriver() {
        return driverThread.get();
    }

    private static URL appiumServerUrl() throws MalformedURLException {
        String serverUrl = System.getProperty("appiumServerUrl", "http://127.0.0.1:4723");
        try {
            // URI.toURL() replaces the deprecated URL(String) constructor,
            // which performs no validation and is deprecated since Java 20.
            return URI.create(serverUrl).toURL();
        } catch (IllegalArgumentException e) {
            throw new MalformedURLException("Invalid appiumServerUrl: " + serverUrl + " (" + e.getMessage() + ")");
        }
    }

    public static void initAndroidDriver() throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "Android");
        caps.setCapability("appium:deviceName", System.getProperty("deviceName", "emulator-5554"));
        caps.setCapability("appium:platformVersion", System.getProperty("platformVersion", "14.0"));
        caps.setCapability("appium:automationName", "UiAutomator2");
        caps.setCapability("appium:app", System.getProperty("appPath", "apps/android/app-debug.apk"));
        caps.setCapability("appium:noReset", false);

        AndroidDriver driver = new AndroidDriver(appiumServerUrl(), caps);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driverThread.set(driver);
    }

    public static void initIOSDriver() throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "iOS");
        caps.setCapability("appium:deviceName", System.getProperty("deviceName", "iPhone 15"));
        caps.setCapability("appium:platformVersion", System.getProperty("platformVersion", "17.0"));
        caps.setCapability("appium:automationName", "XCUITest");
        caps.setCapability("appium:app", System.getProperty("appPath", "apps/ios/app.app"));
        caps.setCapability("appium:noReset", false);

        IOSDriver driver = new IOSDriver(appiumServerUrl(), caps);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driverThread.set(driver);
    }

    public static void quitDriver() {
        if (driverThread.get() != null) {
            driverThread.get().quit();
            driverThread.remove();
        }
    }
}
