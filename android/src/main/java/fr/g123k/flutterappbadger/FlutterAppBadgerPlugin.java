package fr.g123k.flutterappbadger;

import android.content.Context;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.MethodCall;
import me.leolin.shortcutbadger.ShortcutBadger;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

/**
 * FlutterAppBadgerPlugin
 */
public class FlutterAppBadgerPlugin implements MethodCallHandler, FlutterPlugin {

  private Context applicationContext;
  private MethodChannel channel;
  private static final String CHANNEL_NAME = "g123k/flutter_app_badger";

  private Notification.Builder builder;

  /**
   * Plugin registration.
   */

  @Override
  public void onAttachedToEngine(FlutterPluginBinding flutterPluginBinding) {
    channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), CHANNEL_NAME);
    channel.setMethodCallHandler(this);
    applicationContext = flutterPluginBinding.getApplicationContext();
    builder = new Notification.Builder(applicationContext)
    .setContentTitle("")
    .setContentText("")
    .setSmallIcon(R.drawable.ic_launcher);
  }

  @Override
  public void onDetachedFromEngine(FlutterPluginBinding flutterPluginBinding) {
    channel.setMethodCallHandler(null);
    applicationContext = null;
    builder = null;
  }

  @Override
  public void onMethodCall(MethodCall call, Result result) {
    if (call.method.equals("updateBadgeCount")) {
      if (Build.MANUFACTURER.equalsIgnoreCase("Xiaomi")) {
        ShortcutBadger.applyNotification(applicationContext, builder.build(), Integer.valueOf(call.argument("count").toString()));
      }
      ShortcutBadger.applyCount(applicationContext, Integer.valueOf(call.argument("count").toString()));
      result.success(null);
    } else if (call.method.equals("removeBadge")) {
      ShortcutBadger.removeCount(applicationContext);
      result.success(null);
    } else if (call.method.equals("isAppBadgeSupported")) {
      result.success(ShortcutBadger.isBadgeCounterSupported(applicationContext));
    } else {
      result.notImplemented();
    }
  }
}
