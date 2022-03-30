import 'dart:async';

import 'package:flutter/services.dart';

class FlutterAppBadger {
  static const MethodChannel _channel =
      const MethodChannel('g123k/flutter_app_badger');

  static void updateBadgeCount(int count) {
    _channel.invokeMethod('updateBadgeCount', {"count": count});
  }

  static void removeBadge() {
    _channel.invokeMethod('removeBadge');
  }

  static Future<int> getAppBadgeCount() async {
    int? appBadgeCount = await _channel.invokeMethod('getAppBadgeCount');
    return appBadgeCount ?? 0;
  }

  static Future<bool> isAppBadgeSupported() async {
    bool? appBadgeSupported =
        await _channel.invokeMethod('isAppBadgeSupported');
    return appBadgeSupported ?? false;
  }
}
