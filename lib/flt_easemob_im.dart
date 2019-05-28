import 'dart:async';

import 'package:flutter/services.dart';

class FltEasemobIm {
  static const MethodChannel _channel =
      const MethodChannel('flt_easemob_im');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }
}
