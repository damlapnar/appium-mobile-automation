package com.automation.web.config;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.Duration;

/**
 * Drives Chrome on an Android emulator/device via Appium — "mobile web"
 * testing, not native-app automation (see com.automation.app for that).
 * Needs no APK: chromedriver is downloaded automatically and no app
 * install step is required, just an emulator with Chrome (which every
 * standard image ships with).
 */
public class DriverManager {

    private static final Logger log = LoggerFactory.getLogger(DriverManager.class);
    private static final ThreadLocal<AppiumDriver> driverThread = new ThreadLocal<>();

    public static AppiumDriver getDriver() {
        return driverThread.get();
    }

    private static URL appiumServerUrl() throws MalformedURLException {
        String serverUrl = System.getProperty("appiumServerUrl", "http://127.0.0.1:4723");
        try {
            return URI.create(serverUrl).toURL();
        } catch (IllegalArgumentException e) {
            throw new MalformedURLException("Invalid appiumServerUrl: " + serverUrl + " (" + e.getMessage() + ")");
        }
    }

    public static void initAndroidDriver() throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "Android");
        caps.setCapability("appium:deviceName", System.getProperty("deviceName", "emulator-5554"));
        caps.setCapability("appium:automationName", "UiAutomator2");
        caps.setCapability("browserName", "Chrome");
        caps.setCapability("appium:chromedriverAutoDownload", true);
        caps.setCapability("appium:newCommandTimeout", 90);

        log.info("Starting Android/Chrome session: deviceName={}", caps.getCapability("appium:deviceName"));
        AndroidDriver driver = new AndroidDriver(appiumServerUrl(), caps);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driverThread.set(driver);
        log.info("Android/Chrome session started: {}", driver.getSessionId());
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
