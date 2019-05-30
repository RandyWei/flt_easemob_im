import 'dart:async';

import 'package:flutter/services.dart';

import 'emerror.dart';

class FltEasemobIm {
  static const MethodChannel _channel = const MethodChannel('bughub.dev/flt_easemob_im');

  static login(String username, String password) {
    const EventChannel("bughub.dev/flt_easemob_im/events:login")
        .receiveBroadcastStream()
        .listen((event) {
      print("$event");
    }).onError((e) {
      print("$e");
    });
    _channel.invokeMethod('login', {"username": username, "password": password});
  }

  static void init() {
    _channel.invokeMethod('initSDK');
  }
}
