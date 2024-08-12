package com.taskmanager.demo.util;

import com.taskmanager.demo.db.Task;
import com.taskmanager.demo.model.dto.Users;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Converter {
    // convert dto to entity

    public Task convert(com.taskmanager.demo.model.dto.Task task) {
        Task task1 = new Task();
        task1.setUpdatedAt(task.getUpdatedAt());
        task1.setCreatedAt(task.getCreatedAt());
        task1.setDescription(task.getDescription());
        task1.setId(task.getId());
        task1.setStatus(task.getStatus());
        task1.setTitle(task.getTitle());
        return task1;
    }

    // Convert entity to dto

    public com.taskmanager.demo.model.dto.Task convert(Task task) {
        com.taskmanager.demo.model.dto.Task task1 = new com.taskmanager.demo.model.dto.Task();
        task1.setCreatedAt(task.getCreatedAt());
        task1.setDescription(task.getDescription());
        task1.setId(task.getId());
        task1.setStatus(task.getStatus());
        task1.setTitle(task.getTitle());
        task1.setUpdatedAt(task.getUpdatedAt());
        com.taskmanager.demo.db.Users users = task.getAssignedTo();
        Users users1 = convert(users);
        com.taskmanager.demo.db.Users users2 = convert(users1);
        task1.setAssignedTo(users2);
        return task1;
    }

    public Task updateData(Task task1, Task task2) {
        task1.setTitle(task2.getTitle());
        task1.setStatus(task2.getStatus());
        task1.setUpdatedAt(task2.getUpdatedAt());
        task1.setCreatedAt(task2.getCreatedAt());
        task1.setDescription(task2.getDescription());
        return task1;
    }

    public Users convert(com.taskmanager.demo.db.Users users) {
        Users user = new Users();
        user.setUsersId(users.getUsersId());
        user.setIzactive(users.getIzactive());
        user.setFirstName(users.getFirstName());
        user.setLastName(users.getLastName());
        return user;
    }

    public com.taskmanager.demo.db.Users convert(Users user) {
        com.taskmanager.demo.db.Users users = new com.taskmanager.demo.db.Users();
        users.setIzactive(user.getIzactive());
        users.setFirstName(user.getFirstName());
        users.setLastName(user.getLastName());
        users.setUsersId(user.getUsersId());
        return users;
    }
}