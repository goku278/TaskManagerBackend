package com.taskmanager.demo.util;

import com.taskmanager.demo.model.dto.Status;
import com.taskmanager.demo.model.dto.Task;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Vaildator {
    public Status validate(Task task) {
        if (task.getTitle() == null || task.getTitle().isEmpty()) return new Status("Title is mandatory", 500);
        if (task.getStatus() != null && !task.getStatus().isEmpty()) {
            if (task.getStatus().equalsIgnoreCase("Pending") ||
                    task.getStatus().equalsIgnoreCase("In Progress") ||
                    task.getStatus().equalsIgnoreCase("Completed")) {
                return new Status("All fields are validated", 200);
            }
        }
        return new Status("Please input valid status", 500);
    }
}
