package fr.g123k.flutterappbadger;

import android.content.Context;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.MethodCall;
import me.leolin.shortcutbadger.ShortcutBadger;
import android.annotation.TargetApi;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;


/**
 * FlutterAppBadgerPlugin
 */
public class FlutterAppBadgerPlugin implements MethodCallHandler, FlutterPlugin {

  private Context applicationContext;
  private MethodChannel channel;
  private static final String CHANNEL_NAME = "g123k/flutter_app_badger";
  private static final String NOTIFICATION_CHANNEL = "g123k/flutter_app_badger";

  private NotificationManager mNotificationManager;
  private int notificationId = 0;

  /**
   * Plugin registration.
   */

  @Override
  public void onAttachedToEngine(FlutterPluginBinding flutterPluginBinding) {
    channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), CHANNEL_NAME);
    channel.setMethodCallHandler(this);
    applicationContext = flutterPluginBinding.getApplicationContext();
  
    mNotificationManager = (NotificationManager) applicationContext.getSystemService(applicationContext.NOTIFICATION_SERVICE);
  }

  @Override
  public void onDetachedFromEngine(FlutterPluginBinding flutterPluginBinding) {
    channel.setMethodCallHandler(null);
    applicationContext = null;
  }

  @Override
  public void onMethodCall(MethodCall call, Result result) {
    if (call.method.equals("updateBadgeCount")) {
      if (Build.MANUFACTURER.equalsIgnoreCase("Xiaomi")) {
        Notification.Builder  builder = new Notification.Builder(applicationContext)
          .setContentTitle(call.argument("title").toString())
          .setContentText(call.argument("description").toString())
          .setSmallIcon(applicationContext.getApplicationInfo().icon);
        
        mNotificationManager.cancel(notificationId);
        notificationId++;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
          setupNotificationChannel();

          builder.setChannelId(NOTIFICATION_CHANNEL);
        }
        Notification notification = builder.build();
        ShortcutBadger.applyNotification(applicationContext, notification, Integer.valueOf(call.argument("count").toString()));
        mNotificationManager.notify(notificationId, notification);
      }
      else
      {
        Log.d("App Badger: ", "Other Model detected");

      ShortcutBadger.applyCount(applicationContext, Integer.valueOf(call.argument("count").toString()));
      }
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

  @TargetApi(Build.VERSION_CODES.O)
    private void setupNotificationChannel() {
        NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL, "ShortcutBadger Sample",
                NotificationManager.IMPORTANCE_DEFAULT);

        mNotificationManager.createNotificationChannel(channel);
    }
  
}
