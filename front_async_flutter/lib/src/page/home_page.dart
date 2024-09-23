import 'package:flutter/material.dart'
    show
        BuildContext,
        Column,
        ElevatedButton,
        State,
        StatefulWidget,
        Text,
        Widget;
import 'package:front_async_flutter/src/providers/business_provider.dart';
import 'package:provider/provider.dart';

class HomePage extends StatefulWidget {
  const HomePage({super.key});
  @override
  State<HomePage> createState() => _homeState();
}

class _homeState extends State<HomePage> {
  late BusinessProvider _businessProvider;

  int _counter = 0;

  void incrementCounter() {
    setState(() {
      _counter++;
    });
    _businessProvider.callBusiness(_counter);
  }

  @override
  Widget build(BuildContext context) {
    _businessProvider = Provider.of<BusinessProvider>(context, listen: false);
    return Column(
      children: [
        Text('Count: $_counter'),
        ElevatedButton(
          onPressed: incrementCounter,
          child: Text('Increment'),
        ),
      ],
    );
  }
}
