#import "FlutterAppBadgerPlugin.h"

@implementation FlutterAppBadgerPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  FlutterMethodChannel* channel = [FlutterMethodChannel
      methodChannelWithName:@"g123k/flutter_app_badger"
            binaryMessenger:[registrar messenger]];
  FlutterAppBadgerPlugin* instance = [[FlutterAppBadgerPlugin alloc] init];
  [registrar addMethodCallDelegate:instance channel:channel];
}

- (void)enableNotifications {
    if (@available(iOS 10, *)) {
        UNUserNotificationCenter *center = [UNUserNotificationCenter currentNotificationCenter];
        [center requestAuthorizationWithOptions:(UNAuthorizationOptionAlert | UNAuthorizationOptionBadge | UNAuthorizationOptionSound) completionHandler:^(BOOL granted, NSError * _Nullable error){}];
    } else {
        UIUserNotificationSettings* notificationSettings = [UIUserNotificationSettings settingsForTypes:UIUserNotificationTypeAlert | UIUserNotificationTypeBadge | UIUserNotificationTypeSound categories:[[UIApplication sharedApplication] currentUserNotificationSettings].categories];
        [[UIApplication sharedApplication] registerUserNotificationSettings:notificationSettings];
    }
}

- (void)handleMethodCall:(FlutterMethodCall*)call result:(FlutterResult)result {
    [self enableNotifications];
    
  if ([@"updateBadgeCount" isEqualToString:call.method]) {
      NSDictionary *args = call.arguments;
      NSNumber *count = [args objectForKey:@"count"];
      [UIApplication sharedApplication].applicationIconBadgeNumber = count.integerValue;
    result(nil);
  } else if ([@"removeBadge" isEqualToString:call.method]) {
      [UIApplication sharedApplication].applicationIconBadgeNumber = 0;
      result(nil);
  } else if ([@"isAppBadgeSupported" isEqualToString:call.method]) {
      result(@YES);
  } else {
    result(FlutterMethodNotImplemented);
  }
}

@end
