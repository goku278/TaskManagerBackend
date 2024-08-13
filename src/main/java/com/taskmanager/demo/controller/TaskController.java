package com.taskmanager.demo.controller;

import com.taskmanager.demo.model.dto.Status;
import com.taskmanager.demo.model.dto.Task;
import com.taskmanager.demo.service.TaskService;
import com.taskmanager.demo.util.Vaildator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/v1/")
public class TaskController {
    @Autowired
    TaskService taskService;
    @Autowired
    Vaildator vaildator;

    @PostMapping("api/tasks/")
    public ResponseEntity<?> saveTask(@RequestBody Task task) {
        Status status = vaildator.validate(task);
        if (status.getCode() != 200) {
            return ResponseEntity.ok(status);
        }
        return taskService.savePost(task);
    }

    @GetMapping("api/tasks/")
    public ResponseEntity<?> getTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("api/tasks/{id}")
    public ResponseEntity<?> getTask(@PathVariable Long id) {
        return taskService.getTask(id);
    }

    @PutMapping("api/tasks/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id, @RequestBody Task task) {
        return taskService.updateTask(id, task);
    }

    @DeleteMapping("api/tasks/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        return taskService.deleteTask(id);
    }

    @PutMapping("api/addTaskToUser/{userId}")
    public ResponseEntity<?> addTaskToUser(@PathVariable Long userId, @RequestBody Task task) {
        Status status = vaildator.validate(task);
        if (status.getCode() != 200) {
            return ResponseEntity.ok(status);
        }
        return taskService.addTaskToUser(userId, task);
    }

    @PutMapping("api/addTaskToUser/{taskId}/{userId}")
    public ResponseEntity<?> addTaskToUser(@PathVariable Long taskId, @PathVariable Long userId) {
        return taskService.addUserToTask(taskId, userId);
    }

    // Extra tasks (optional)
    @GetMapping("api/filtering/{status}")
    public ResponseEntity<?> getTasksBasedOnFilterStatus(@PathVariable String status) {
        return taskService.getTasksByStatus(status);
    }

    @GetMapping("api/filtering2/{userName}")
    public ResponseEntity<?> getTasksBasedOnFilterUserName(@PathVariable String userName) {
        return taskService.getTasksByUserName(userName);
    }

    @GetMapping("api/sort/createdDate")
    public ResponseEntity<?> sortTasksByCreatedDate() {
        return taskService.sortTasksByCreatedDate();
    }

    @GetMapping("api/sort2/firstName")
    public ResponseEntity<?> sortTasksByUserFirstName() {
        return taskService.sortTasksByUserFirstName();
    }
}