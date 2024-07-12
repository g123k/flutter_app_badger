## [1.5.2] - 15th Mart 2024

* Removed unnecessary requestAuthorizationWithOptions for iOS, this plugin doesn't need it to show badges. This should be used only for remote push notification.

## [1.5.1] - 2nd January 2024

* Fixed an issue with missing Gradle namespace and other related issues.
* Resolved an issue with the Java target SDK.
* Adjusted Dart SDK constraint to exclusively support Dart null-safety and Flutter null-safety versions.
* Corrected the usage of old Flutter embedding in the example app.
* Updated the wrapper to support the new Gradle version.

## [1.5.0] - 16th September 2022

* PR #52 (Don't overwrite categories on iOS)
* PR #32 (A Future is now returned)
* PR #65 (jCenter -> Maven)

## [1.4.0] - 08th April 2022

* PR #61 (macOS support)
 
## [1.3.0] - 15th September 2021

* PR #44 (Use UNUserNotificationCenter instead of deprecated UIUserNotificationSettings on iOS 10+) 
* PR #47 (Feature/android v2)

## [1.2.0] - 15th March 2021

* Support for null safety 

## [1.1.2] - 15th December 2019

* Support the new plugin pubspec format
* On Android, the custom dependency to ShortcutBadger is removed, since unused  

## [1.1.1] - 3rd December 2019

* Migration of the sample to Android X

## [1.1.0] - 11th November 2019

* Migration to Android X (thanks to orknist) + Android Target SDK = 29

## [1.0.3+2] - 13th September 2019

* README updated

## [1.0.3] - 25th June 2019
  
* Android: Migration to the latest version of the ShortcutBadger library (1.1.22)

## [1.0.2] - 5th July 2018
  
* Android: Include the [PR #268](https://github.com/leolin310148/ShortcutBadger/pull/268) to support Oreo devices.  
  
## [1.0.1] - 10th April 2018  
  
* Fix a bug with the Android build.  
  
## [1.0.0] - 10th April 2018  
  
* Initial Release.
