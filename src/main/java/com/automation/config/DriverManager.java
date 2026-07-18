package com.automation.config;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.Duration;

public class DriverManager {

    private static final Logger log = LoggerFactory.getLogger(DriverManager.class);
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
        // Default target: Sauce Labs' open-source "My Demo App" — see apps/README.md
        // for exactly where to download it and why this suite is built against it.
        caps.setCapability("appium:app", System.getProperty("appPath", "apps/android/mda.apk"));
        caps.setCapability("appium:noReset", false);

        log.info(
            "Starting Android session: deviceName={}, platformVersion={}, app={}",
            caps.getCapability("appium:deviceName"),
            caps.getCapability("appium:platformVersion"),
            caps.getCapability("appium:app")
        );
        AndroidDriver driver = new AndroidDriver(appiumServerUrl(), caps);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driverThread.set(driver);
        log.info("Android session started: {}", driver.getSessionId());
    }

    public static void initIOSDriver() throws MalformedURLException {
        // The iOS build of the same app exists (see apps/README.md) but its
        // page objects are NOT verified the way the Android ones are: its
        // storyboards and view controllers set no accessibilityIdentifier
        // anywhere, so LoginPage's @iOSXCUITFindBy locators are unconfirmed
        // guesses, not something read out of the app's real source.
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "iOS");
        caps.setCapability("appium:deviceName", System.getProperty("deviceName", "iPhone 15"));
        caps.setCapability("appium:platformVersion", System.getProperty("platformVersion", "17.0"));
        caps.setCapability("appium:automationName", "XCUITest");
        caps.setCapability("appium:app", System.getProperty("appPath", "apps/ios/mda.app"));
        caps.setCapability("appium:noReset", false);

        log.info(
            "Starting iOS session: deviceName={}, platformVersion={}, app={}",
            caps.getCapability("appium:deviceName"),
            caps.getCapability("appium:platformVersion"),
            caps.getCapability("appium:app")
        );
        IOSDriver driver = new IOSDriver(appiumServerUrl(), caps);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driverThread.set(driver);
        log.info("iOS session started: {}", driver.getSessionId());
    }

    public static void quitDriver() {
        AppiumDriver driver = driverThread.get();
        if (driver != null) {
            log.info("Quitting session: {}", driver.getSessionId());
            driver.quit();
            driverThread.remove();
        }
    }
}
