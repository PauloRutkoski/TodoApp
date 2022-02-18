import 'package:bloc/bloc.dart';
import 'package:equatable/equatable.dart';
import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';

part 'settings_state.dart';

class SettingsCubit extends Cubit<SettingsState> {
  late SharedPreferences preferences;

  SettingsCubit() : super(SettingsInitial()) {
    loadSettings();
  }

  Future<void> loadSettings() async {
    preferences = await SharedPreferences.getInstance();
    int? value = preferences.getInt('theme');
    if (value == null) {
      value = ThemeMode.light.index;
      await preferences.setInt('theme', value);
    }
    emit(SettingsLoaded(theme: ThemeMode.values[value]));
  }

  Future<void> switchTheme() async {
    ThemeMode theme =
        state.theme == ThemeMode.dark ? ThemeMode.light : ThemeMode.dark;
    await preferences.setInt('theme', theme.index);
    emit(state.copyWith(theme: theme));
  }
}
