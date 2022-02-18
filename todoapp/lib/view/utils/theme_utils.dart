import 'package:flutter/material.dart';

class ThemeUtils {
  static const Color _primaryLight = Color(0XFFcb6ce6);
  static const Color _secondaryLight = Color(0XFF8844ee);

  static const Color _primaryDark = Color(0XFF5A5A5A);
  static const Color _secondaryDark = Color(0XFF464646);

  static Color getPrimary(BuildContext context) {
    if (Theme.of(context).brightness == Brightness.light) {
      return _primaryLight;
    }
    return _primaryDark;
  }

  static Color getSecondary(BuildContext context) {
    if (Theme.of(context).brightness == Brightness.light) {
      return _secondaryLight;
    }
    return _secondaryDark;
  }

  static ThemeData get whiteTheme => ThemeData(
      primaryColor: _primaryLight,
      textSelectionTheme: TextSelectionThemeData(
        cursorColor: _secondaryLight,
        selectionColor: _secondaryLight.withOpacity(0.2),
      ),
      brightness: Brightness.light,
      inputDecorationTheme: InputDecorationTheme(
        fillColor: Colors.white.withOpacity(0.8),
      ),
      colorScheme: ColorScheme.fromSwatch().copyWith(
        primary: _primaryLight,
        secondary: _secondaryLight,
        brightness: Brightness.light,
      ),
      scaffoldBackgroundColor: Colors.grey[50],
      cardColor: Colors.white,
      iconTheme: IconThemeData(color: Colors.grey[800]),
      cardTheme: CardTheme(
        elevation: 5,
        shadowColor: _secondaryLight.withOpacity(0.3),
        margin: const EdgeInsets.fromLTRB(0, 5, 0, 5),
      ),
      textTheme: TextTheme(
        bodyMedium: TextStyle(
          fontSize: 18,
          color: Colors.grey[800],
        ),
      ));

  static ThemeData get darkTheme => ThemeData(
      primaryColor: _primaryDark,
      textSelectionTheme: TextSelectionThemeData(
        cursorColor: Colors.white,
        selectionColor: Colors.white.withOpacity(0.6),
      ),
      inputDecorationTheme: InputDecorationTheme(
        hintStyle: const TextStyle(
          color: Colors.white,
        ),
        fillColor: Colors.black.withOpacity(0.3),
      ),
      colorScheme: ColorScheme.fromSwatch().copyWith(
        primary: _primaryDark,
        secondary: _secondaryDark,
        brightness: Brightness.dark,
      ),
      brightness: Brightness.dark,
      scaffoldBackgroundColor: _secondaryDark,
      cardColor: _primaryDark,
      cardTheme: CardTheme(
        elevation: 5,
        shadowColor: _secondaryDark.withOpacity(0.3),
        margin: const EdgeInsets.fromLTRB(0, 5, 0, 5),
      ),
      iconTheme: const IconThemeData(color: Colors.white),
      textTheme: const TextTheme(
        bodyMedium: TextStyle(
          fontSize: 18,
          color: Colors.white,
        ),
      ));
}
