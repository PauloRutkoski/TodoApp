import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:todoapp/view/settings/cubits/settings_cubit.dart';
import 'package:todoapp/view/utils/theme_utils.dart';

class TodoListAppBar extends StatelessWidget {
  const TodoListAppBar({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    const double height = 125;
    return SliverAppBar(
      pinned: true,
      expandedHeight: height,
      floating: true,
      titleSpacing: 0,
      centerTitle: false,
      collapsedHeight: kToolbarHeight,
      flexibleSpace: Container(
        padding: MediaQuery.of(context).padding,
        height: height + MediaQuery.of(context).padding.top,
        decoration: BoxDecoration(
          gradient: LinearGradient(
            colors: [
              ThemeUtils.getSecondary(context),
              ThemeUtils.getPrimary(context),
            ],
          ),
          boxShadow: [
            BoxShadow(
              color: ThemeUtils.getSecondary(context).withOpacity(0.8),
              offset: const Offset(0, 2),
              spreadRadius: 1,
              blurRadius: 10,
            )
          ],
        ),
        child: SingleChildScrollView(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.start,
            crossAxisAlignment: CrossAxisAlignment.start,
            children: _appBarBody(context),
          ),
        ),
      ),
    );
  }

  List<Widget> _appBarBody(BuildContext context) {
    return [
      title(context),
      const SizedBox(
        height: 10,
      ),
      input(context),
    ];
  }

  Widget title(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.only(
        top: 5,
        left: 10,
      ),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: [
          const Text(
            "To-Do",
            style: TextStyle(
              fontSize: 24,
              color: Colors.white,
              fontWeight: FontWeight.bold,
            ),
          ),
          themeModeSwitcher(context),
        ],
      ),
    );
  }

  Widget themeModeSwitcher(BuildContext context) {
    return BlocBuilder<SettingsCubit, SettingsState>(
      builder: (context, state) {
        return IconButton(
          onPressed: () async {
            await context.read<SettingsCubit>().switchTheme();
          },
          icon: Icon(
            state.theme == ThemeMode.light
                ? Icons.mode_night_outlined
                : Icons.wb_sunny_outlined,
            color: Colors.white,
          ),
        );
      },
    );
  }

  Widget input(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.only(left: 8.0, right: 8.0),
      child: TextFormField(
        style: const TextStyle(
          fontSize: 18,
        ),
        decoration: InputDecoration(
          border: OutlineInputBorder(
            borderRadius: BorderRadius.circular(10),
            borderSide: BorderSide(
              color: ThemeUtils.getSecondary(context),
              width: 2,
            ),
          ),
          focusedBorder: OutlineInputBorder(
            borderRadius: BorderRadius.circular(10),
            borderSide: BorderSide(
              color: ThemeUtils.getSecondary(context),
              width: 2,
            ),
          ),
          hintText: "What's next?",
          filled: true,
          isCollapsed: true,
          contentPadding: const EdgeInsets.fromLTRB(10, 15, 10, 15),
        ),
      ),
    );
  }
}
