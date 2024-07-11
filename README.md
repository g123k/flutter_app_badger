# Flutter App Badger plugin (UNMAINTAINED)

[![Pub](https://img.shields.io/pub/v/flutter_app_badger.svg)](https://pub.dartlang.org/packages/flutter_app_badger)

This plugin for [Flutter](https://flutter.io) adds the ability to change the badge of the app in the launcher.
It supports iOS, macOS, and some Android devices (the official API does not support the feature, even on Oreo).

<p align="center">
  <img src="https://raw.githubusercontent.com/g123k/flutter_app_badger/master/assets/ios.png" alt="Android badge" style="margin:auto" width="600" 
height="228">
</p>

<p align="center">
  <img src="https://raw.githubusercontent.com/g123k/flutter_app_badger/master/assets/android.png" alt="Android badge" style="margin:auto" width="600" 
height="322">
</p>


## Getting Started

### iOS

On iOS, the notification permission is required to update the badge.
It is automatically asked when the badge is added or removed.

Please also add the following to your <your project>/ios/Runner/Info.plist:
```xml
<key>UIBackgroundModes</key>
    <array>
        <string>remote-notification</string>
    </array>
```

## macOS

On macOS, the notification permission is required to update the badge.
It is automatically asked when the badge is added or removed.

Please also add the following to your <your project>/macos/Runner/Info.plist:
```xml
<key>NSUserNotificationAlertStyle</key>
<string>banner</string>
```

### Android

On Android, no official API exists to show a badge in the launcher. But some devices (Samsung, HTC...) support the feature.
Thanks to the [Shortcut Badger library](https://github.com/leolin310148/ShortcutBadger/), ~ 16 launchers are supported.


### Dart

First, you just have to import the package in your dart files with:
```dart
import 'package:flutter_app_badger/flutter_app_badger.dart';
```

Then you can add a badge:
```dart
FlutterAppBadger.updateBadgeCount(1);
```

Remove a badge:
```dart
FlutterAppBadger.removeBadge();
```

Or just check if the device supports this feature with:
```dart
FlutterAppBadger.isAppBadgeSupported();
```
