# Test target: Sauce Labs' "My Demo App"

This suite is written against Sauce Labs' own open-source demo app, not a
fictional one. The Android page objects (`LoginPage`, `NavigationPage`,
`ProductCatalogPage`) were verified directly against that app's public
source — resource IDs, validation messages, and navigation flow all read
out of the real `fragment_login.xml`, `LoginFragment.java`, and
`MainActivity.java`, not guessed.

## Android (verified)

Download `mda.apk` from the latest release and place it here as
`apps/android/mda.apk` (gitignored — binaries aren't committed):

<https://github.com/saucelabs/my-demo-app-android/releases/latest>

Look for an asset named like `mda-<version>-<build>.apk` (not the
`-androidTest-` one, that's the instrumentation test APK, not the app).

Test accounts, and what to expect logging in with each one:

| Username             | Password   | Result                                              |
| --------------------- | ---------- | ---------------------------------------------------- |
| `bod@example.com`     | `10203040` | Logs in, lands back on the product catalog           |
| `alice@example.com`   | `10203040` | Rejected: "Sorry this user has been locked out."      |
| `visual@example.com`  | `10203040` | Logs in, but the app renders deliberate visual glitches |

The app has no login wall — it opens straight to the product catalog.
Login/Logout lives inside the hamburger menu (`menuIV` → drawer → the
"Log In"/"Log Out" item, `content-desc="Login Menu Item"` /
`"Logout Menu Item"`), which is why every test in `LoginTest` opens the
menu via `NavigationPage` before touching `LoginPage`.

## iOS (unverified — needs work)

Sauce Labs also publishes an iOS build of the same app
(<https://github.com/saucelabs/my-demo-app-ios/releases/latest> — grab the
`*.Simulator.zip` asset for local Simulator testing) and
`DriverManager.initIOSDriver()` is wired up to launch it. But unlike the
Android app, neither its Storyboards nor its Swift view controllers set any
`accessibilityIdentifier` — there's nothing to read a real locator strategy
out of the way there was for Android. `LoginPage`'s `@iOSXCUITFindBy`
annotations were removed rather than filled in with unverified guesses.

Whoever picks up iOS support next will need Xcode + a Simulator to inspect
the real accessibility tree (Xcode's Accessibility Inspector, or
`appium inspector` pointed at a running session) and write the iOS locators
against what's actually there.
