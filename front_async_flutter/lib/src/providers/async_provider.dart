// ignore_for_file: avoid_print

import 'package:flutter/material.dart';
import 'package:front_async_flutter/src/application/app_config.dart';
import 'package:http/http.dart' as http;
import 'package:shared_preferences/shared_preferences.dart';
import 'dart:convert';
import 'package:channel_sender_client/channel_sender_client.dart';

class AsyncProvider extends ChangeNotifier {
  String _apiKey = '1865f43a0549ca50d341dd9ab8b29f49';
  // ignore: prefer_final_fields
  //String _urlBusinessService = "http://localhost:8080/api";

  late String urlBusinessService;
  late SharedPreferences _prefs;
  late AsyncClient _asyncClient;
  final String evenListen = "businessEvent";

  Future<SharedPreferences> getPrefs() async {
    return await SharedPreferences.getInstance();
  }

  getInitPreferences() async {
    _prefs = await getPrefs();
  }

  AsyncProvider(BuildContext context) {
    print('MoviesProvider inicializado');
    //urlBusinessService = AppConfig?.of(context).businessUrl;
    this.getInitPreferences();
    this.getCredentils();
  }

  validateCredentials() {
    if (_prefs.getString("channelRef") != null &&
        _prefs.getString("channelSecret") != null) {
      this.initChannel();
    } else {
      getCredentils();
    }
  }

  getCredentils() async {
    final url =
        Uri.parse('http://localhost:8080/api/credentials?user_ref=${_apiKey}');
    final response = await http.get(url);
    if (response != null) {
      Map<String, dynamic> decodedData = jsonDecode(response.body);

      print(decodedData);
      print(decodedData['channelRef']);
      print(decodedData['channelSecret']);
      _prefs.setString("channelRef", decodedData['channelRef']);
      _prefs.setString("channelSecret", decodedData['channelSecret']);
      initChannel();
      // _prefs.setString("channelRef", value)
    }
    print("response");
    print(response);
  }

  initChannel() {
    print("initChannel");
    print(_prefs.getString('channelRef'));
    print(_prefs.getString('channelSecret'));
    String? channelRef = _prefs.getString('channelRef');
    String? channelSecret = _prefs.getString('channelSecret');
    final conf = AsyncConfig(
      socketUrl: 'ws://localhost:8082/ext/socket',
      enableBinaryTransport: false,
      channelRef: channelRef!,
      channelSecret: channelSecret!,
    );

    _asyncClient = AsyncClient(conf).connect();
    _asyncClient.subscribeTo(evenListen, (evenResult) {
      print("Async Event success");
      print(evenResult);
    }, onError: (err) {
      print("Async Event Error");
      print(err);
    });
  }
}
