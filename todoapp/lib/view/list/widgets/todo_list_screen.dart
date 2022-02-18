import 'package:flutter/material.dart';

import 'package:todoapp/view/list/widgets/todo_card.dart';
import 'package:todoapp/view/list/widgets/todo_list_app_bar.dart';

class TodoListScreen extends StatefulWidget {
  const TodoListScreen({Key? key}) : super(key: key);

  @override
  State<TodoListScreen> createState() => _TodoListScreenState();
}

class _TodoListScreenState extends State<TodoListScreen> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: _buildBody(context),
    );
  }

  _buildBody(BuildContext context) {
    return Scrollbar(
      child: CustomScrollView(
        physics: const BouncingScrollPhysics(),
        slivers: [
          const TodoListAppBar(),
          _listView(),
        ],
      ),
    );
  }

  Widget _listView() {
    return SliverPadding(
      padding: const EdgeInsets.fromLTRB(15, 15, 15, 0),
      sliver: SliverReorderableList(
        itemBuilder: (context, index) {
          String value = list[index];
          return TodoCard(
            title: value,
            index: index,
            key: ValueKey(value),
          );
        },
        itemCount: list.length,
        onReorder: (oldIndex, newIndex) {
          setState(() {
            String text = list.removeAt(oldIndex);
            newIndex = newIndex > oldIndex ? newIndex - 1 : newIndex;
            list.insert(newIndex, text);
          });
        },
      ),
    );
  }

  List list = [
    "Teste 1",
    "Teste 2",
    "Teste 3",
    "Teste 4",
    "Teste 5",
    "Teste 6",
    "Teste 7",
    "Teste 8",
    "Teste 9",
    "Teste 10",
    "Teste 11",
    "Teste 12",
    "Teste 13",
    "Teste 14",
    "Teste 15",
    "Teste 16",
    "Teste 17",
    "Teste 18",
    "Teste 19",
    "Teste 20",
    "Teste 21",
  ];
}
