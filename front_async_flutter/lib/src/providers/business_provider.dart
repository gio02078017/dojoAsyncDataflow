// ignore_for_file: avoid_print

import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:shared_preferences/shared_preferences.dart';

class BusinessProvider extends ChangeNotifier {
  late SharedPreferences _prefs;

  Future<SharedPreferences> getPrefs() async {
    return await SharedPreferences.getInstance();
  }

  getInitPreferences() async {
    _prefs = await getPrefs();
  }

  BusinessProvider(BuildContext context) {
    print('BusinessProvider inicializado');
    this.getInitPreferences();
    this.callBusiness(5000);
  }

  callBusiness(int delay) async {
    if (_prefs.getString('channelRef') != null) {
      final url = Uri.parse(
          'http://localhost:8080/api/business?channel_ref=${_prefs.getString('channelRef')}&${delay}');
      final response = await http.get(url);
      print("response callBusiness");
      print(response);
    }
  }
}
