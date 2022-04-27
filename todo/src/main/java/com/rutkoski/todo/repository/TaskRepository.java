package com.rutkoski.todo.repository;

import com.rutkoski.todo.enums.TaskStatus;
import com.rutkoski.todo.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface TaskRepository extends JpaRepository<Task, Long> {

    Page<Task> findAllByUserId(Long userId, Pageable pageable);

    @Query("select Max(task.position) from Task task where task.user.id = :user")
    Long findTopPositionByUserId(@Param("user")Long userId);

    @Modifying
    @Query("update Task task set task.status = :status where task.id = :id")
    void updateStatus(@Param("id") Long id, @Param("status") TaskStatus status);

    @Modifying
    @Query("update Task task set task.title = :title where task.id = :id")
    void updateTitle(@Param("id") Long id, @Param("title") String title);
}
