package fr.g123k.flutterappbadger;

import android.content.Context;

import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.PluginRegistry.Registrar;
import me.leolin.shortcutbadger.ShortcutBadger;

/**
 * FlutterAppBadgerPlugin
 */
public class FlutterAppBadgerPlugin implements MethodCallHandler {

  private final Context context;

  private FlutterAppBadgerPlugin(Context context) {
    this.context = context;
  }

  /**
   * Plugin registration.
   */
  public static void registerWith(Registrar registrar) {
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "g123k/flutter_app_badger");
    channel.setMethodCallHandler(new FlutterAppBadgerPlugin(registrar.activeContext()));
  }

  @Override
  public void onMethodCall(MethodCall call, Result result) {
    if (call.method.equals("updateBadgeCount")) {
      ShortcutBadger.applyCount(context, Integer.valueOf(call.argument("count").toString()));
      result.success(null);
    } else if (call.method.equals("removeBadge")) {
      ShortcutBadger.removeCount(context);
      result.success(null);
    } else if (call.method.equals("isAppBadgeSupported")) {
      result.success(ShortcutBadger.isBadgeCounterSupported(context));
    } else {
      result.notImplemented();
    }
  }
}
