# Appium Mobile Automation Framework

![Appium](https://img.shields.io/badge/Appium-663399?style=flat-square&logo=appium&logoColor=white)
![Java](https://img.shields.io/badge/Java-ED8B00?style=flat-square&logo=openjdk&logoColor=white)
![Android](https://img.shields.io/badge/Android-3DDC84?style=flat-square&logo=android&logoColor=white)
![GitHub Actions](https://img.shields.io/badge/CI-GitHub_Actions-2088FF?style=flat-square&logo=github-actions&logoColor=white)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

Mobile automation framework built with Appium, Java, and TestNG using Page
Object Model. Two independent, coexisting test suites, each a real,
verified target rather than a fictional one:

- **`com.automation.app`** — native-app testing against Sauce Labs' own
  open-source ["My Demo App"](https://github.com/saucelabs/my-demo-app-android)
  (a real downloaded APK). See [apps/README.md](apps/README.md) for exactly
  how the page objects were verified against that app's real source.
- **`com.automation.web`** — mobile-web testing: Chrome on an Android
  emulator, driving [saucedemo.com](https://www.saucedemo.com) — the same
  site the sibling `playwright-web-automation` project tests directly in a
  browser, here through Appium's mobile-web bridge instead. Needs no app
  install.

## Why Appium?

Appium extends the same WebDriver protocol Selenium uses to native and
hybrid mobile apps, so a tester who already thinks in terms of
`driver.findElement()` and Page Objects doesn't need a second mental model
for mobile — just a different driver behind the same interface. That's also
why one framework can hold both suites here: `com.automation.app` and
`com.automation.web` share `SwipeHelper` and the same Page Object pattern
despite driving a native app and a browser respectively.

The cross-platform promise is real but only half-delivered so far —
`DriverManager.initIOSDriver()` exists and Sauce Labs publishes an iOS build
of the same demo app, but as [apps/README.md](apps/README.md) documents,
nobody has sat down with Xcode's Accessibility Inspector to write real iOS
locators yet. The architecture supports a third suite; it isn't written.

## Features

- **Two coexisting suites** — native-app and mobile-web, sharing only the
  generic `SwipeHelper` gesture utility
- **Page Object Model** — each suite has its own `BasePage`/`LoginPage`
  (native app's uses `@AndroidFindBy`/`WebElement`; mobile-web's uses plain
  `By` CSS selectors, since it's really just DOM automation through Chrome)
- **Thread-Safe Driver** — `ThreadLocal` per suite for parallel execution
- **TestNG Suite** — Unit Tests / Native App Tests / Mobile Web Tests as
  independent `<test>` blocks in one `testng.xml`
- **Unit-testable logic** — `SwipeHelper`'s coordinate math is separated
  from live driver calls specifically so it can run without a device
  (`SwipeHelperTest`, takes the driver as a parameter so either suite can
  use it)
- **Checkstyle** — a small curated ruleset (`config/checkstyle.xml`), not
  the bundled defaults (see the comment in `pom.xml` for why)
- **CI/CD** — GitHub Actions: a fast `unit-tests` job (checkstyle + unit
  tests, no emulator) and an `android-tests` job that downloads the real
  APK and runs both suites against one booted emulator
- **Dependency automation** — Dependabot for Maven + GitHub Actions

iOS support exists at the driver level in `com.automation.app`
(`DriverManager.initIOSDriver()`, capabilities for `saucelabs/my-demo-app-ios`)
but its page objects are **not** implemented — that app sets no
accessibility identifiers anywhere in its Storyboards or view controllers,
so there was nothing to verify locators against the way there was for
Android. See [apps/README.md](apps/README.md).

## Project Structure

```
appium-mobile-automation/
├── src/main/java/com/automation/
│   ├── app/                         # Native-app suite (Sauce Labs' My Demo App)
│   │   ├── config/DriverManager.java
│   │   ├── data/TestUsers.java
│   │   ├── pages/{BasePage,LoginPage,NavigationPage,ProductCatalogPage}.java
│   │   └── tests/{BaseTest,LoginTest}.java
│   ├── web/                         # Mobile-web suite (saucedemo.com via Chrome)
│   │   ├── config/DriverManager.java
│   │   ├── data/SauceDemoUsers.java
│   │   ├── pages/{BasePage,LoginPage}.java
│   │   └── tests/{BaseTest,LoginTest}.java
│   └── utils/
│       └── SwipeHelper.java         # Shared; coordinate math is unit-tested
├── src/test/java/com/automation/utils/
│   └── SwipeHelperTest.java         # Runs without a device
├── src/test/resources/testng.xml    # Unit Tests + Native App Tests + Mobile Web Tests
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
- Android Studio / emulator (or a real device)
- For `com.automation.app` only: Sauce Labs' "My Demo App" APK — see
  [apps/README.md](apps/README.md)

## Getting Started

```bash
npm install -g appium
appium driver install uiautomator2
appium

# in another terminal, with an emulator/device already running:
mkdir -p apps/android
# download mda.apk per apps/README.md into apps/android/mda.apk
# (only needed for the native-app suite — mobile-web needs just Chrome)
```

## Running Tests

```bash
# Unit tests only — no emulator or Appium server needed
mvn test -Dtest=SwipeHelperTest

# Everything in testng.xml: Unit Tests always run; both Android suites
# need Appium + an emulator/device (native-app also needs the APK above)
mvn test -Dplatform=android -DdeviceName=emulator-5554

# Checkstyle
mvn checkstyle:check
```

Note: `-Dtest=<Class>` combined with this project's custom `testng.xml`
drops the `<parameter name="platform">` it declares, so `LoginTest`'s
`@Parameters`-based setup silently matches nothing rather than actually
running. Filter by test name only works for parameter-free classes like
`SwipeHelperTest`; for the Android suites, run `mvn test` and let
`testng.xml` pick what to include.
