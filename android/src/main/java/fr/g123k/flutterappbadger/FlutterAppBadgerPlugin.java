package fr.g123k.flutterappbadger;

import android.content.Context;

import androidx.annotation.NonNull;

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
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    channel.setMethodCallHandler(null);
    applicationContext = null;
  }

  @Override
  public void onMethodCall(MethodCall call, @NonNull Result result) {
    switch (call.method) {
      case "updateBadgeCount":
        ShortcutBadger.applyCount(applicationContext, Integer.valueOf(call.argument("count").toString()));
        result.success(null);
        break;
      case "removeBadge":
        ShortcutBadger.removeCount(applicationContext);
        result.success(null);
        break;
      case "isAppBadgeSupported":
        result.success(ShortcutBadger.isBadgeCounterSupported(applicationContext));
        break;
      default:
        result.notImplemented();
        break;
    }
  }
}
