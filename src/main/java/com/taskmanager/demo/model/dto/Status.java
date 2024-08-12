package com.taskmanager.demo.model.dto;

import java.util.List;

public class Status {
    private String message;
    private int code;

    private List<Task> task;

    private List<Users> usersList;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Status(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public Status(List<Users> usersList, int code) {
        this.code = code;
        this.usersList = usersList;
    }

    public List<Task> getTask() {
        return task;
    }

    public void setTask(List<Task> task) {
        this.task = task;
    }

    public List<Users> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<Users> usersList) {
        this.usersList = usersList;
    }

    public Status(int code, List<Task> task) {
        this.code = code;
        this.task = task;
    }

    @Override
    public String toString() {
        return "Status{" +
                "message='" + message + '\'' +
                ", code=" + code +
                ", task=" + task +
                ", usersList=" + usersList +
                '}';
    }
}
