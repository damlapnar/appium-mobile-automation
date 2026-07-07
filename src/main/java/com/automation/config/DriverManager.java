package com.automation.config;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class DriverManager {

    private static final ThreadLocal<AppiumDriver> driverThread = new ThreadLocal<>();

    public static AppiumDriver getDriver() {
        return driverThread.get();
    }

    public static void initAndroidDriver() throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "Android");
        caps.setCapability("appium:deviceName", System.getProperty("deviceName", "emulator-5554"));
        caps.setCapability("appium:automationName", "UiAutomator2");
        caps.setCapability("browserName", "Chrome");
        caps.setCapability("appium:chromedriverAutoDownload", true);
        caps.setCapability("appium:newCommandTimeout", 90);

        AndroidDriver driver = new AndroidDriver(
            new URL("http://127.0.0.1:4723"),
            caps
        );
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
