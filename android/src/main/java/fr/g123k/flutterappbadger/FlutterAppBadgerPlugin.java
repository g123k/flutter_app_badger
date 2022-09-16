package fr.g123k.flutterappbadger;

import android.content.Context;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.MethodCall;
import android.preference.PreferenceManager;
import android.content.SharedPreferences;
import me.leolin.shortcutbadger.ShortcutBadger;

/**
 * FlutterAppBadgerPlugin
 */
public class FlutterAppBadgerPlugin implements MethodCallHandler, FlutterPlugin {

  private Context applicationContext;
  private MethodChannel channel;
  private static final String CHANNEL_NAME = "g123k/flutter_app_badger";

  /**
   * Plugin registration.
   */

  @Override
  public void onAttachedToEngine(FlutterPluginBinding flutterPluginBinding) {
    channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), CHANNEL_NAME);
    channel.setMethodCallHandler(this);
    applicationContext = flutterPluginBinding.getApplicationContext();
  }

  @Override
  public void onDetachedFromEngine(FlutterPluginBinding flutterPluginBinding) {
    channel.setMethodCallHandler(null);
    applicationContext = null;
  }

  @Override
  public void onMethodCall(MethodCall call, Result result) {
    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(applicationContext);
    int count = prefs.getInt("flutter_app_badger_count_key", 0);
    if (call.method.equals("updateBadgeCount")) {
      count = count + 1;
      ShortcutBadger.applyCount(applicationContext, Integer.valueOf(call.argument("count").toString()));
      SharedPreferences.Editor editor = prefs.edit();
      editor.putInt("flutter_app_badger_count_key", count);
      editor.commit();
      result.success(null);
    } else if (call.method.equals("removeBadge")) {
      ShortcutBadger.removeCount(applicationContext);
      count = 0;
      SharedPreferences.Editor editor = prefs.edit();
      editor.putInt("flutter_app_badger_count_key", count);
      editor.commit();
      result.success(null);
    } else if (call.method.equals("isAppBadgeSupported")) {
      result.success(ShortcutBadger.isBadgeCounterSupported(applicationContext));
    } else if (call.method.equals("getAppBadgeCount")) {
      result.success(count);
    } else {
      result.notImplemented();
    }
  }
}
