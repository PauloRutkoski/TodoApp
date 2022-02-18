import 'package:flutter/material.dart';

class TodoCard extends StatelessWidget {
  final String? title;
  final int index;
  const TodoCard({Key? key, required this.index, this.title}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Card(
      child: Padding(
        padding: const EdgeInsets.all(10.0),
        child: Row(
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          children: [
            Text('$title'),
            ReorderableDragStartListener(
              index: index,
              child: const Icon(
                Icons.drag_indicator,
              ),
            )
          ],
        ),
      ),
    );
  }
}
