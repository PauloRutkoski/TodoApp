package com.rutkoski.todo.to;

import com.rutkoski.todo.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskTO {
    private Long id;
    private String title;
    private Long position;
    private TaskStatus status;
}
