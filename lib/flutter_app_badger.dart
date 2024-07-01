import 'dart:async';

import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

class FlutterAppBadger {
  static const MethodChannel _channel =
      const MethodChannel('g123k/flutter_app_badger');

  static Future<void> updateBadgeCount(int count) async {
    final mock = _mockUpdateBadgeCount;
    if (mock != null) {
      await mock(count);
      return;
    }

    return _channel.invokeMethod('updateBadgeCount', {"count": count});
  }

  static Future<void> removeBadge() async {
    final mock = _mockRemoveBadge;
    if (mock != null) {
      await mock();
      return;
    }

    return _channel.invokeMethod('removeBadge');
  }

  static Future<bool> isAppBadgeSupported() async {
    final mock = _mockIsAppBadgeSupported;
    if (mock != null) {
      return mock();
    }

    bool? appBadgeSupported =
        await _channel.invokeMethod('isAppBadgeSupported');
    return appBadgeSupported ?? false;
  }

  static Future<void> enableNotifications() async {
    final mock = _mockEnableNotifications;
    if (mock != null) {
      await mock();
      return;
    }

    return _channel.invokeMethod('enableNotifications');
  }

  static Future<void> Function(int count)? _mockUpdateBadgeCount;
  static Future<void> Function()? _mockRemoveBadge;
  static Future<bool> Function()? _mockIsAppBadgeSupported;
  static Future<bool> Function()? _mockEnableNotifications;

  @visibleForTesting
  static void setMocks({
    Future<void> Function(int count)? updateBadgeCount,
    Future<void> Function()? removeBadge,
    Future<bool> Function()? isAppBadgeSupported,
    Future<bool> Function()? enableNotifications,
  }) {
    _mockUpdateBadgeCount = updateBadgeCount;
    _mockRemoveBadge = removeBadge;
    _mockIsAppBadgeSupported = isAppBadgeSupported;
    _mockEnableNotifications = enableNotifications;
  }

  @visibleForTesting
  static void clearMocks() {
    _mockUpdateBadgeCount = null;
    _mockRemoveBadge = null;
    _mockIsAppBadgeSupported = null;
    _mockEnableNotifications = null;
  }
}
