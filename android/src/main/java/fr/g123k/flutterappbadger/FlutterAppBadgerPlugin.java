package fr.g123k.flutterappbadger;

import android.content.Context;
import android.os.Build;
import android.app.NotificationManager;
import android.app.NotificationChannel;
import android.app.Notification;
import androidx.core.app.NotificationCompat;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.MethodCall;
import me.leolin.shortcutbadger.ShortcutBadger;

/**
 * FlutterAppBadgerPlugin
 */
public class FlutterAppBadgerPlugin implements MethodCallHandler, FlutterPlugin {

  private Context applicationContext;
  private MethodChannel channel;
  private static final String CHANNEL_NAME = "g123k/flutter_app_badger";
  private static final String CHANNEL_ID = "badge_channel_id";

  /**
   * Plugin registration.
   */

  @Override
  public void onAttachedToEngine(FlutterPluginBinding flutterPluginBinding) {
    channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), CHANNEL_NAME);
    channel.setMethodCallHandler(this);
    applicationContext = flutterPluginBinding.getApplicationContext();

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      int importance = NotificationManager.IMPORTANCE_MIN;
      NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "badge_channel", importance);
      NotificationManager notificationManager = (NotificationManager) applicationContext
          .getSystemService(Context.NOTIFICATION_SERVICE);
      notificationManager.createNotificationChannel(channel);
    }
  }

  @Override
  public void onDetachedFromEngine(FlutterPluginBinding flutterPluginBinding) {
    channel.setMethodCallHandler(null);
    applicationContext = null;
  }

  @Override
  public void onMethodCall(MethodCall call, Result result) {
    if (call.method.equals("updateBadgeCount")) {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        Notification notification = new NotificationCompat.Builder(applicationContext,
            CHANNEL_ID)
            .setContentTitle("badge title (not visible)")
            .setContentText("badge text (not visible)")
            .setSmallIcon(getDrawableResourceId(applicationContext, "ic_launcher"))
            .setNumber(Integer.valueOf(call.argument("count").toString()))
            .build();

        NotificationManager notificationManager = (NotificationManager) applicationContext
            .getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(1001, notification);
      } else {
        ShortcutBadger.applyCount(applicationContext,
            Integer.valueOf(call.argument("count").toString()));
      }

      result.success(null);
    } else if (call.method.equals("removeBadge")) {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        NotificationManager notificationManager = (NotificationManager) applicationContext
            .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
      } else {
        ShortcutBadger.removeCount(applicationContext);
      }

      result.success(null);
    } else if (call.method.equals("isAppBadgeSupported")) {
      result.success(ShortcutBadger.isBadgeCounterSupported(applicationContext));
    } else {
      result.notImplemented();
    }
  }

  private static int getDrawableResourceId(Context context, String name) {
    return context.getResources().getIdentifier(name, "drawable", context.getPackageName());
  }
}
