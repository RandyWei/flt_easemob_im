import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:flt_easemob_im/flt_easemob_im.dart';

void main() {
  const MethodChannel channel = MethodChannel('flt_easemob_im');

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await FltEasemobIm.platformVersion, '42');
  });
}
