package com.taskmanager.demo.repo;

import com.taskmanager.demo.db.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query(value = "select * from Task where status like ?1", nativeQuery = true)
    List<Task> getTasksByStatus(String status);

    @Query(value = "select * from Task where user_id like ?1", nativeQuery = true)
    Task getTaskByUserId(Long ids);

    @Query(value = "SELECT id, created_at, description, status, title, updated_at, user_id FROM Task ORDER BY created_at ASC", nativeQuery = true)
    List<Object[]> sortByCreatedDate();

    @Query(value = "SELECT id, created_at, description, status, title, updated_at, user_id FROM Task WHERE user_id like ?1", nativeQuery = true)
    List<Object[]> getTasksByIds(Long id);

//    @Query(value = "SELECT id, created_at, description, status, title, updated_at FROM Task ORDER BY created_at ASC", nativeQuery = true)
//    List<Object[]> sortByUserFirstName();
}