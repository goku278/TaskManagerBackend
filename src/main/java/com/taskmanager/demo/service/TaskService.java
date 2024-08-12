package com.taskmanager.demo.service;

import com.taskmanager.demo.db.Users;
import com.taskmanager.demo.model.dto.Status;
import com.taskmanager.demo.model.dto.Task;
import com.taskmanager.demo.repo.TaskRepository;
import com.taskmanager.demo.repo.UsersRepository;
import com.taskmanager.demo.util.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    Converter converter;

    public ResponseEntity<?> savePost(Task task) {
        try {
            com.taskmanager.demo.db.Task task1 = converter.convert(task);
            Users users = new Users();
            users.setUsersId(-1L);
            task1.setAssignedTo(users);
            com.taskmanager.demo.db.Task task2 = taskRepository.save(task1);
            return ResponseEntity.ok(new Status("Task created successfully", 200));
        } catch (Exception e) {
            return ResponseEntity.ok(new Status("Internal Server Error\n\nwith cause" + e.getMessage(), 500));
        }
    }

    public ResponseEntity<?> getAllTasks() {
        try {
            List<com.taskmanager.demo.db.Task> tasks = taskRepository.findAll();
            List<Task> taskList = new ArrayList<>();
            for (com.taskmanager.demo.db.Task task : tasks) {
                Task task1 = converter.convert(task);
                taskList.add(task1);
            }
            if (taskList == null || taskList.isEmpty()) {
                return ResponseEntity.ok(new Status("No Task Found", 200));
            } else {
                return ResponseEntity.ok(new Status(200, taskList));
            }
        } catch (Exception e) {
            return ResponseEntity.ok(new Status("Internal Server error\n\nwith cause" + e.getMessage(), 500));
        }
    }

    public ResponseEntity<?> getTask(Long id) {
        try {
            com.taskmanager.demo.db.Task task = taskRepository.findById(id).orElse(null);
            if (task == null) {
                return ResponseEntity.ok(new Status("Task with id " + id + " not found", 200));
            } else {
                ArrayList<Task> tasks = new ArrayList<>();
                Task task1 = converter.convert(task);
                tasks.add(task1);
                return ResponseEntity.ok(new Status(200, tasks));
            }
        } catch (Exception e) {
            return ResponseEntity.ok(new Status("Internal Server error\n\nwith cause" + e.getMessage(), 500));
        }
    }

    public ResponseEntity<?> updateTask(Long id, Task task2) {
        try {
            com.taskmanager.demo.db.Task task = taskRepository.findById(id).orElse(null);
            if (task == null) {
                return ResponseEntity.ok(new Status("Task with id " + id + " not found", 200));
            } else {
                com.taskmanager.demo.db.Task task1 = converter.convert(task2);
                converter.updateData(task, task1);
                taskRepository.save(task);
                return ResponseEntity.ok(new Status("Task with id " + id + " has been updated successfully", 200));
            }
        } catch (Exception e) {
            return ResponseEntity.ok(new Status("Internal Server error\n\nwith cause" + e.getMessage(), 500));
        }
    }

    public ResponseEntity<?> deleteTask(Long id) {
        try {
            com.taskmanager.demo.db.Task task = taskRepository.findById(id).orElse(null);
            if (task == null) {
                return ResponseEntity.ok(new Status("Task with id " + id + " not found", 200));
            } else {
                taskRepository.delete(task);
                return ResponseEntity.ok(new Status("Task with id " + id + " has been deleted successfully", 200));
            }
        } catch (Exception e) {
            return ResponseEntity.ok(new Status("Internal Server error\n\nwith cause" + e.getMessage(), 500));
        }
    }

    public ResponseEntity<?> addUserToTask(Long taskId, Long userId) {
        try {
            com.taskmanager.demo.db.Users users = usersRepository.findById(userId).orElse(null);
            com.taskmanager.demo.db.Task task = taskRepository.findById(taskId).orElse(null);
            if (task == null) return ResponseEntity.ok(new Status("Task with id " + taskId + " does not exist", 404));
            task.setAssignedTo(users);
            com.taskmanager.demo.db.Task task2 = taskRepository.save(task);

            return ResponseEntity.ok(new Status("User with id " + userId + " has been added to a task", 200));
        } catch (Exception e) {
            return ResponseEntity.ok(new Status("Internal Server Error, with error cause\n\n" + e.getMessage(), 500));
        }
    }

    public ResponseEntity<?> getTasksByStatus(String status) {
        List<com.taskmanager.demo.db.Task> tasks = taskRepository.getTasksByStatus(status);
        if (tasks == null || tasks.isEmpty()) {
            return ResponseEntity.ok(new Status("Task not found with status " + status, 404));
        } else {
            List<Task> taskList = new ArrayList<>();
            for (com.taskmanager.demo.db.Task task : tasks) {
                Task task1 = converter.convert(task);
                taskList.add(task1);
            }
            return ResponseEntity.ok(new Status(200, taskList));
        }
    }

    public ResponseEntity<?> addTaskToUser(Long userId, Task task) {
        try {
            Users users = usersRepository.findById(userId).orElse(null);
            if (users == null) {
                return ResponseEntity.ok(new Status("User not found with id " + userId, 404));
            }
            com.taskmanager.demo.db.Task task1 = converter.convert(task);
            task1.setAssignedTo(users);
            taskRepository.save(task1);
            return ResponseEntity.ok(new Status("Task assigned to user with id  " + userId, 200));
        } catch (Exception e) {
            return ResponseEntity.ok(new Status("Internal Server Error, with cause  " + e.getMessage(), 500));
        }
    }

    public ResponseEntity<?> getTasksByUserName(String userName) {
        try {
            if (userName == null || userName.isEmpty())
                return ResponseEntity.ok(new Status("Please share user name", 500));
            List<Long> userIds = usersRepository.getAllUserIdWithUserName(userName);
            if (userIds == null || userIds.isEmpty()) {
                return ResponseEntity.ok(new Status("User not found with user name " + userName, 404));
            }
            List<com.taskmanager.demo.db.Task> tasks = new ArrayList<>();
            for (Long ids : userIds) {
                System.out.println("ids == " + ids);
                if (ids != null) {
                    com.taskmanager.demo.db.Task task = taskRepository.getTaskByUserId(ids);
                    tasks.add(task);
                }
            }
            if (tasks != null && !tasks.isEmpty()) {
                List<Task> taskList = new ArrayList<>();
                for (com.taskmanager.demo.db.Task task : tasks) {
                    Task task1 = converter.convert(task);
                    taskList.add(task1);
                }
                return ResponseEntity.ok(new Status(200, taskList));
            } else return ResponseEntity.ok(new Status("Tasks with user name " + userName + " not found", 404));
        } catch (Exception e) {
            return ResponseEntity.ok(new Status("Internal Server Error, with cause  " + e.getMessage(), 500));
        }
    }

    public ResponseEntity<?> sortTasksByCreatedDate() {
        try {
            List<Object[]> tasks = taskRepository.sortByCreatedDate();
            List<Task> taskList = new ArrayList<>();
            for (Object[] task : tasks) {
                Task task1 = new Task();
                Long id = ((Number) task[0]).longValue();
                ZonedDateTime createdAt = ((Timestamp) task[1]).toInstant().atZone(ZoneId.systemDefault());
                String description = (String) task[2];
                String status = (String) task[3];
                String title = (String) task[4];
                ZonedDateTime updatedAt = ((Timestamp) task[5]).toInstant().atZone(ZoneId.systemDefault());
                Long userId = ((Number) task[6]).longValue();

                System.out.println("Task ID: " + id);
                System.out.println("Created At: " + createdAt);
                System.out.println("Description: " + description);
                System.out.println("Status: " + status);
                System.out.println("Title: " + title);
                System.out.println("Updated At: " + updatedAt);
                System.out.println("userId: " + userId);

                task1.setStatus(status);
                task1.setTitle(title);
                task1.setCreatedAt(createdAt);
                task1.setId(id);
                task1.setUpdatedAt(updatedAt);
                task1.setDescription(description);
                Users users = usersRepository.findById(userId).orElse(null);
                if (users != null) {
                    Users users1 = new Users();
                    users1.setIzactive(users.getIzactive());
                    users1.setUsersId(users.getUsersId());
                    users1.setLastName(users.getLastName());
                    users1.setFirstName(users.getFirstName());
                    users1.setTimeZone(users.getTimeZone());
                    users1.setUsersId(users.getUsersId());
                    users1.setTasks(null);
                    task1.setAssignedTo(users1);
                }
                taskList.add(task1);
            }
            if (taskList == null || taskList.isEmpty()) return ResponseEntity.ok(new Status("No Tasks Found", 404));
            return ResponseEntity.ok(new Status(200, taskList));
        } catch (Exception e) {
            return ResponseEntity.ok(new Status("Internal Server Error, with cause " + e.getMessage(), 500));
        }

    }

    public ResponseEntity<?> sortTasksByUserFirstName() {
        try {
            List<Long> userIds = usersRepository.sortByUserName();
            System.out.println("userIds == " + userIds);
            List<Task> tasks = new ArrayList<>();
            for (Long id : userIds) {
                List<Object[]> task = taskRepository.getTasksByIds(id);
                for (Object[] task1 : task) {
                    Task task2 = new Task();
                    Long id2 = ((Number) task1[0]).longValue();
                    ZonedDateTime createdAt = ((Timestamp) task1[1]).toInstant().atZone(ZoneId.systemDefault());
                    String description = (String) task1[2];
                    String status = (String) task1[3];
                    String title = (String) task1[4];
                    ZonedDateTime updatedAt = ((Timestamp) task1[5]).toInstant().atZone(ZoneId.systemDefault());
                    Long userId = ((Number) task1[6]).longValue();

                    System.out.println("Task ID: " + id2);
                    System.out.println("Created At: " + createdAt);
                    System.out.println("Description: " + description);
                    System.out.println("Status: " + status);
                    System.out.println("Title: " + title);
                    System.out.println("Updated At: " + updatedAt);
                    System.out.println("userId: " + userId);

                    task2.setStatus(status);
                    task2.setTitle(title);
                    task2.setCreatedAt(createdAt);
                    task2.setId(id);
                    task2.setUpdatedAt(updatedAt);
                    task2.setDescription(description);

                    Users users = usersRepository.findById(userId).orElse(null);
                    if (users != null) {
                        Users users1 = new Users();
                        users1.setIzactive(users.getIzactive());
                        users1.setUsersId(users.getUsersId());
                        users1.setLastName(users.getLastName());
                        users1.setFirstName(users.getFirstName());
                        users1.setTimeZone(users.getTimeZone());
                        users1.setUsersId(users.getUsersId());
                        users1.setTasks(null);
                        task2.setAssignedTo(users1);
                    }

                    tasks.add(task2);
                }
            }
            if (tasks == null || tasks.isEmpty()) return ResponseEntity.ok(new Status("Tasks not found", 404));
            return ResponseEntity.ok(new Status(200, tasks));
        } catch (Exception e) {
            return ResponseEntity.ok(new Status("Internal Server Error", 500));
        }
    }
}
