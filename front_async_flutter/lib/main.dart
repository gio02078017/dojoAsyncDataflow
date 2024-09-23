import 'package:flutter/material.dart';
import 'package:front_async_flutter/src/page/home_page.dart';
import 'package:front_async_flutter/src/providers/async_provider.dart';
import 'package:front_async_flutter/src/providers/business_provider.dart';
import 'package:provider/provider.dart';
import 'package:flutter_dotenv/flutter_dotenv.dart' as DotEnv;

void main() async {
  runApp(AppState());
}

class AppState extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MultiProvider(
      providers: [
        ChangeNotifierProvider(
            create: (_) => AsyncProvider(context), lazy: false),
        ChangeNotifierProvider(
            create: (_) => BusinessProvider(context), lazy: false),
      ],
      child: MainApp(),
    );
  }
}

class MainApp extends StatelessWidget {
  const MainApp({super.key});

  @override
  Widget build(BuildContext context) {
    return const MaterialApp(
      home: Scaffold(
        body: SafeArea(
          child: Center(
              child: Column(
            children: [Text('Async Data Flow'), HomePage()],
          )),
        ),
      ),
    );
  }
}
