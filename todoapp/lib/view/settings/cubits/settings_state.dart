part of 'settings_cubit.dart';

@immutable
abstract class SettingsState extends Equatable {
  final ThemeMode theme;

  const SettingsState({this.theme = ThemeMode.light});

  @override
  List<Object?> get props => [theme];

  SettingsLoaded copyWith({ThemeMode? theme}) {
    return SettingsLoaded(theme: theme ?? this.theme);
  }
}

class SettingsInitial extends SettingsState {
  @override
  List<Object?> get props => [];
}

class SettingsLoaded extends SettingsState {
  const SettingsLoaded({required ThemeMode theme}) : super(theme: theme);

  @override
  List<Object?> get props => [super.theme];
}
