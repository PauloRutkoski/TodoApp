package com.rutkoski.todo.to;

import com.rutkoski.todo.enums.TaskStatus;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class TaskTO {
    private Long id;
    private String title;
    private Long position;
    private TaskStatus status;
}
