package com.taskmanager.demo.controller;

import com.taskmanager.demo.model.dto.Users;
import com.taskmanager.demo.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/v1/")
public class UsersController {
    @Autowired
    UsersService usersService;
    @PostMapping("api/addUser/")
    public ResponseEntity<?> addUser(@RequestBody Users users) {
        System.out.println("users = " + users);
        return usersService.addUser(users);
    }

    @GetMapping("api/getUsers")
    public ResponseEntity<?> getUsers() {
        return usersService.getUsers();
    }

    // pagination

    @GetMapping("api/getUsers/{fromPageNo}/{toPageNo}")
    public ResponseEntity<?> getUsers(@PathVariable Long fromPageNo, @PathVariable Long toPageNo) {
        return usersService.getUsers(fromPageNo, toPageNo);
    }

    @GetMapping("api/getUser/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        return usersService.getUser(id);
    }

    @DeleteMapping("api/deleteUser/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        return usersService.deleteUser(id);
    }
}
