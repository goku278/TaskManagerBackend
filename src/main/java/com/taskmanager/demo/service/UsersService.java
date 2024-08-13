package com.taskmanager.demo.service;

import com.taskmanager.demo.model.dto.Status;
import com.taskmanager.demo.model.dto.Users;
import com.taskmanager.demo.repo.TaskRepository;
import com.taskmanager.demo.repo.UsersRepository;
import com.taskmanager.demo.util.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsersService {
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    Converter converter;

    public ResponseEntity<?> addUser(Users users) {
        try {
            com.taskmanager.demo.db.Users user = converter.convert(users);
            usersRepository.save(user);
            return ResponseEntity.ok(new Status("Users added successfully", 200));
        } catch (Exception e) {
            return ResponseEntity.ok(new Status("Error while adding user, cause of error is " + e.getMessage(), 500));
        }
    }

    public ResponseEntity<?> getUsers() {
        try {
            List<com.taskmanager.demo.db.Users> users = usersRepository.findAll();
            if (users == null || users.isEmpty()) return ResponseEntity.ok(new Status("Users not found", 404));
            List<Users> usersList = new ArrayList<>();
            for (com.taskmanager.demo.db.Users users1 : users) {
                Users users2 = converter.convert(users1);
                usersList.add(users2);
            }
            return ResponseEntity.ok(new Status(usersList, 200));
        } catch (Exception e) {
            return ResponseEntity.ok(new Status("Error while adding user, cause of error is " + e.getMessage(), 500));
        }
    }

    public ResponseEntity<?> getUsers(Long fromPageNo, Long toPageNo) {
        try {
            List<com.taskmanager.demo.db.Users> users = usersRepository.findAll();
            List<com.taskmanager.demo.db.Users> users2 = new ArrayList<>();
            long count = 1;
            for (com.taskmanager.demo.db.Users users1 : users) {
                if (count >= fromPageNo && count <= toPageNo) {
                    users2.add(users1);
                    count++;
                }
            }
            if (users2 == null || users2.isEmpty()) return ResponseEntity.ok(new Status("Users not found", 404));
            List<Users> usersList = new ArrayList<>();
            for (com.taskmanager.demo.db.Users users1 : users2) {
                Users users4 = converter.convert(users1);
                usersList.add(users4);
            }
            return ResponseEntity.ok(new Status(usersList, 200));
        } catch (Exception e) {
            return ResponseEntity.ok(new Status("Error while adding user, cause of error is " + e.getMessage(), 500));
        }
    }

    public ResponseEntity<?> getUser(Long id) {
        try {
            com.taskmanager.demo.db.Users users = usersRepository.findById(id).orElse(null);
            if (users == null) return ResponseEntity.ok(new Status("User with id " + id + " not found", 404));
            List<Users> usersList = new ArrayList<>();
            Users users1 = converter.convert(users);
            usersList.add(users1);
            return ResponseEntity.ok(new Status(usersList, 200));
        } catch (Exception e) {
            return ResponseEntity.ok(new Status("Error while adding user, cause of error is " + e.getMessage(), 500));
        }
    }

    public ResponseEntity<?> deleteUser(Long id) {
        try {
            usersRepository.deleteById(id);
            return ResponseEntity.ok(new Status("User Deleted Successfully", 200));
        } catch (Exception e) {
            return ResponseEntity.ok(new Status("Internal Server Error due to " + e.getMessage(), 500));
        }

    }
}
