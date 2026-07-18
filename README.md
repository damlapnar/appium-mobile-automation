# Appium Mobile Automation Framework

![Appium](https://img.shields.io/badge/Appium-663399?style=flat-square&logo=appium&logoColor=white)
![Java](https://img.shields.io/badge/Java-ED8B00?style=flat-square&logo=openjdk&logoColor=white)
![Android](https://img.shields.io/badge/Android-3DDC84?style=flat-square&logo=android&logoColor=white)
![GitHub Actions](https://img.shields.io/badge/CI-GitHub_Actions-2088FF?style=flat-square&logo=github-actions&logoColor=white)

Mobile automation framework built with Appium, Java, and TestNG using Page
Object Model. Written against a real, public target — Sauce Labs' own
open-source ["My Demo App"](https://github.com/saucelabs/my-demo-app-android)
— not a fictional one; see [apps/README.md](apps/README.md) for exactly how
that was verified and how to get the app itself.

## Features

- **Page Object Model** — `LoginPage`, `NavigationPage`, `ProductCatalogPage`
  extending a common `BasePage`, all verified against the real app's source
- **Thread-Safe Driver** — `ThreadLocal` for parallel execution
- **TestNG Suite** — unit tests + Android tests as independent `<test>` blocks
- **Unit-testable logic** — `SwipeHelper`'s coordinate math is separated
  from live driver calls specifically so it can run without a device
  (`SwipeHelperTest`)
- **Checkstyle** — a small curated ruleset (`config/checkstyle.xml`), not
  the bundled defaults (see the comment in `pom.xml` for why)
- **CI/CD** — GitHub Actions: a fast `unit-tests` job (checkstyle + unit
  tests, no emulator) and an `android-tests` job that downloads the real
  APK and runs against a booted emulator
- **Dependency automation** — Dependabot for Maven + GitHub Actions

iOS support exists at the driver level (`DriverManager.initIOSDriver()`,
capabilities for `saucelabs/my-demo-app-ios`) but its page objects are
**not** implemented — that app sets no accessibility identifiers anywhere
in its Storyboards or view controllers, so there was nothing to verify
locators against the way there was for Android. See
[apps/README.md](apps/README.md).

## Project Structure

```
appium-mobile-automation/
├── src/main/java/com/automation/
│   ├── config/
│   │   └── DriverManager.java       # Thread-safe driver management + logging
│   ├── data/
│   │   └── TestUsers.java           # Centralized test accounts
│   ├── pages/
│   │   ├── BasePage.java            # Common interactions
│   │   ├── LoginPage.java           # Login screen POM
│   │   ├── NavigationPage.java      # Hamburger menu / side drawer
│   │   └── ProductCatalogPage.java  # Post-login landing screen
│   ├── tests/
│   │   ├── BaseTest.java            # Setup/teardown
│   │   └── LoginTest.java           # Login test cases
│   └── utils/
│       └── SwipeHelper.java         # Swipe gestures (coordinate math is unit-tested)
├── src/test/java/com/automation/utils/
│   └── SwipeHelperTest.java         # Runs without a device
├── src/test/resources/
│   └── testng.xml                   # Unit Tests + Android Tests suites
├── config/checkstyle.xml
├── apps/README.md                   # Real target app, accounts, iOS gap
└── .github/
    ├── workflows/mobile-tests.yml
    └── dependabot.yml
```

## Prerequisites

- Java 17+
- Maven 3.8+
- Appium Server 2.x with the `uiautomator2` driver
- Android Studio / emulator (or a real device) for Android tests
- Sauce Labs' "My Demo App" APK — see [apps/README.md](apps/README.md)

## Getting Started

```bash
npm install -g appium
appium driver install uiautomator2
appium

# in another terminal, with an emulator/device already running:
mkdir -p apps/android
# download mda.apk per apps/README.md into apps/android/mda.apk
```

## Running Tests

```bash
# Unit tests only — no emulator or Appium server needed
mvn test -Dtest=SwipeHelperTest

# Android (needs Appium + an emulator/device + apps/android/mda.apk)
mvn test -Dplatform=android -DdeviceName=emulator-5554

# Everything in testng.xml (unit tests always run; Android tests need the
# environment above)
mvn test

# Checkstyle
mvn checkstyle:check
```
