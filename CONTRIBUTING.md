# Contributing to appium-mobile-automation

Thank you for your interest in contributing!

## Getting Started

```bash
git clone https://github.com/damlapnar/appium-mobile-automation.git
cd appium-mobile-automation
mvn compile
```

`mvn compile` alone doesn't need Appium, an emulator, or the app — it's the
fastest way to confirm your toolchain is set up correctly.

## The Target App

This suite is written against Sauce Labs' real, open-source "My Demo App,"
not a fictional one — see [apps/README.md](apps/README.md) for where to get
it, the real test accounts, and (importantly) the iOS gap: that app's
Storyboards and view controllers set no accessibility identifiers anywhere,
so `LoginPage` has no verified iOS locators yet.

When adding a new page object or test, verify it against the app's actual
public source (layout XML / `Fragment.java` for Android) rather than
guessing at locators — that's how the current page objects were built, and
how the original fictional ones (which pointed at a `com.example.app` that
never existed) got replaced.

## Running Tests

```bash
# Unit tests — no emulator or Appium server needed
mvn test -Dtest=SwipeHelperTest

# Android — needs Appium running, an emulator/device, and
# apps/android/mda.apk in place (see apps/README.md)
mvn test -Dplatform=android -DdeviceName=emulator-5554
```

## Linting

```bash
mvn checkstyle:check
```

Runs in its own fast CI job (`unit-tests`) alongside the swipe-math unit
tests — neither needs the emulator that `android-tests` requires. See the
comment in `pom.xml` for why this project uses a small custom
`config/checkstyle.xml` instead of Checkstyle's bundled `google_checks.xml`
or `sun_checks.xml`.

## Guidelines

- Verify new page objects against the real app's source, not assumptions
- Add tests for any new functionality
- Keep commits small and focused with descriptive messages
- Open an issue before submitting large changes

## Pull Request Process

1. Fork the repository
2. Create a feature branch (`git checkout -b feat/your-feature`)
3. Commit your changes with a descriptive message
4. Push to your fork and open a Pull Request against `main`
5. Ensure all CI checks pass

## Reporting Bugs

Open a GitHub Issue with:

- Steps to reproduce
- Expected vs actual behavior
- Environment details (OS, Java version, Android/emulator version)
