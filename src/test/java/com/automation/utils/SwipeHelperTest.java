package com.automation.utils;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Unit tests for the swipe coordinate math — the one part of this
 * automation project that doesn't need a live Appium session/device to
 * verify, so it's the one part that gets to be actually executed and
 * checked here rather than just read against a real app's source.
 */
public class SwipeHelperTest {

    @DataProvider(name = "screenSizes")
    public Object[][] screenSizes() {
        // Real device resolutions only — a degenerate size like 1x1 makes
        // (int) (height * 0.8) and (int) (height * 0.2) both truncate to
        // the same value, which isn't a SwipeHelper bug, just not a
        // meaningful input for a helper that only ever sees real screens.
        return new Object[][] {
            {1080, 1920},
            {720, 1280},
            {1440, 3120},
        };
    }

    @Test(dataProvider = "screenSizes")
    public void upSwipeGoesFromLowerToUpperScreen(int width, int height) {
        SwipeHelper.SwipeCoordinates c = SwipeHelper.upCoordinates(width, height);

        assertEquals(c.startX(), width / 2);
        assertEquals(c.endX(), width / 2);
        assertEquals(c.startY(), (int) (height * 0.8));
        assertEquals(c.endY(), (int) (height * 0.2));
        assertTrueStartsBelowEnd(c);
    }

    @Test(dataProvider = "screenSizes")
    public void downSwipeGoesFromUpperToLowerScreen(int width, int height) {
        SwipeHelper.SwipeCoordinates c = SwipeHelper.downCoordinates(width, height);

        assertEquals(c.startX(), width / 2);
        assertEquals(c.endX(), width / 2);
        assertEquals(c.startY(), (int) (height * 0.2));
        assertEquals(c.endY(), (int) (height * 0.8));
        assertTrueStartsAboveEnd(c);
    }

    @Test(dataProvider = "screenSizes")
    public void upAndDownAreMirrorImages(int width, int height) {
        SwipeHelper.SwipeCoordinates up = SwipeHelper.upCoordinates(width, height);
        SwipeHelper.SwipeCoordinates down = SwipeHelper.downCoordinates(width, height);

        assertEquals(up.startY(), down.endY());
        assertEquals(up.endY(), down.startY());
    }

    private void assertTrueStartsBelowEnd(SwipeHelper.SwipeCoordinates c) {
        org.testng.Assert.assertTrue(c.startY() > c.endY(), "an upward swipe should start below where it ends");
    }

    private void assertTrueStartsAboveEnd(SwipeHelper.SwipeCoordinates c) {
        org.testng.Assert.assertTrue(c.startY() < c.endY(), "a downward swipe should start above where it ends");
    }
}
