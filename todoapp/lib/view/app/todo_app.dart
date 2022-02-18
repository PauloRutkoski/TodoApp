import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:todoapp/view/list/widgets/todo_list_screen.dart';
import 'package:todoapp/view/utils/theme_utils.dart';

import '../settings/cubits/settings_cubit.dart';

class TodoApp extends StatelessWidget {
  const TodoApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return BlocBuilder<SettingsCubit, SettingsState>(
      builder: (context, state) {
        return MaterialApp(
          theme: ThemeUtils.whiteTheme,
          darkTheme: ThemeUtils.darkTheme,
          themeMode: state.theme,
          debugShowCheckedModeBanner: false,
          home: home(state),
        );
      },
    );
  }

  Widget home(SettingsState state) {
    if (state is SettingsInitial) {
      return const Scaffold(
        body: Center(
          child: CircularProgressIndicator(),
        ),
      );
    }
    return const TodoListScreen();
  }
}
