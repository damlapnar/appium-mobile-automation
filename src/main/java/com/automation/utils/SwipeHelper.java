package com.automation.utils;

import com.automation.config.DriverManager;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

import java.time.Duration;
import java.util.List;

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

    public static void swipeUp() {
        Dimension size = DriverManager.getDriver().manage().window().getSize();
        perform(upCoordinates(size.width, size.height));
    }

    public static void swipeDown() {
        Dimension size = DriverManager.getDriver().manage().window().getSize();
        perform(downCoordinates(size.width, size.height));
    }

    private static void perform(SwipeCoordinates c) {
        AppiumDriver driver = DriverManager.getDriver();
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1)
            .addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), c.startX(), c.startY()))
            .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
            .addAction(finger.createPointerMove(Duration.ofMillis(600), PointerInput.Origin.viewport(), c.endX(), c.endY()))
            .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(List.of(swipe));
    }
}
