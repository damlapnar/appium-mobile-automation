# Appium Mobile Automation Framework

![Appium](https://img.shields.io/badge/Appium-663399?style=flat-square&logo=appium&logoColor=white)
![Java](https://img.shields.io/badge/Java-ED8B00?style=flat-square&logo=openjdk&logoColor=white)
![Android](https://img.shields.io/badge/Android-3DDC84?style=flat-square&logo=android&logoColor=white)
![iOS](https://img.shields.io/badge/iOS-000000?style=flat-square&logo=apple&logoColor=white)
[![CI](https://github.com/damlapnar/appium-mobile-automation/actions/workflows/mobile-tests.yml/badge.svg)](https://github.com/damlapnar/appium-mobile-automation/actions/workflows/mobile-tests.yml)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

Cross-platform mobile automation framework supporting Android and iOS. Built with Appium, Java, and TestNG using Page Object Model with platform-specific locator support.

## Features

- **Cross-Platform** — single codebase for Android and iOS
- **Platform-Specific Locators** — `@AndroidFindBy` + `@iOSXCUITFindBy` annotations
- **Thread-Safe Driver** — `ThreadLocal` for parallel execution
- **Page Object Model** — maintainable page abstractions extending `BasePage`
- **TestNG Suite** — parameterized platform selection
- **CI/CD** — GitHub Actions with Android emulator

## Project Structure

```
appium-mobile-automation/
├── src/main/java/com/automation/
│   ├── config/
│   │   └── DriverManager.java     # Thread-safe driver management
│   ├── pages/
│   │   ├── BasePage.java          # Common interactions
│   │   └── LoginPage.java         # Login screen POM
│   └── tests/
│       ├── BaseTest.java          # Setup/teardown
│       └── LoginTest.java         # Login test cases
└── src/test/resources/
    └── testng.xml                 # Suite configuration
```

## Prerequisites

- Java 17+
- Maven 3.8+
- Appium Server 2.x
- Android Studio (for Android) / Xcode (for iOS)

## Getting Started

```bash
# Install Appium
npm install -g appium
appium driver install uiautomator2
appium driver install xcuitest

# Start Appium server
appium
```

## Running Tests

```bash
# Android
mvn test -Dplatform=android -DdeviceName=emulator-5554

# iOS
mvn test -Dplatform=ios -DdeviceName="iPhone 15"

# Both platforms in parallel
mvn test
```
