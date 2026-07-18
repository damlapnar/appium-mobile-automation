package com.automation.utils;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

import java.time.Duration;
import java.util.List;

/**
 * Shared between both the native-app and mobile-web test suites — takes the
 * driver as a parameter rather than reading a specific package's
 * DriverManager, since either suite's driver works the same way here.
 */
public class SwipeHelper {

    /** start/end coordinates for a swipe, kept separate from any live driver so the math is unit-testable. */
    record SwipeCoordinates(int startX, int startY, int endX, int endY) {
    }

    static SwipeCoordinates upCoordinates(int width, int height) {
        int startY = (int) (height * 0.8);
        int endY = (int) (height * 0.2);
        int centerX = width / 2;
        return new SwipeCoordinates(centerX, startY, centerX, endY);
    }

    static SwipeCoordinates downCoordinates(int width, int height) {
        int startY = (int) (height * 0.2);
        int endY = (int) (height * 0.8);
        int centerX = width / 2;
        return new SwipeCoordinates(centerX, startY, centerX, endY);
    }

    public static void swipeUp(AppiumDriver driver) {
        Dimension size = driver.manage().window().getSize();
        perform(driver, upCoordinates(size.width, size.height));
    }

    public static void swipeDown(AppiumDriver driver) {
        Dimension size = driver.manage().window().getSize();
        perform(driver, downCoordinates(size.width, size.height));
    }

    private static void perform(AppiumDriver driver, SwipeCoordinates c) {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1)
            .addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), c.startX(), c.startY()))
            .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
            .addAction(finger.createPointerMove(Duration.ofMillis(600), PointerInput.Origin.viewport(), c.endX(), c.endY()))
            .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(List.of(swipe));
    }
}
