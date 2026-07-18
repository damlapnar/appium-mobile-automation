package com.automation.app.data;

/**
 * Accounts built into Sauce Labs' "My Demo App" (the app this suite targets —
 * see apps/README.md). Verified against the app's public source: all three
 * accounts share the same password; only the username differs. alice logs in
 * successfully as far as field validation goes, then the app rejects her with
 * a "locked out" message rather than a plain validation error.
 */
public final class TestUsers {

    public static final String PASSWORD = "10203040";

    public static final String STANDARD_USERNAME = "bod@example.com";
    public static final String LOCKED_OUT_USERNAME = "alice@example.com";
    public static final String VISUAL_GLITCH_USERNAME = "visual@example.com";

    private TestUsers() {
    }
}
