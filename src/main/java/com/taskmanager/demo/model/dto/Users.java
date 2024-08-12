package com.taskmanager.demo.model.dto;

import org.springframework.stereotype.Component;

@Component
public class Users {
    private Long usersId;
    private String firstName;
    private String lastName;
    private String izactive;

    public Users() {
    }

    public Users(Long usersId, String firstName, String lastName, String izactive) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.izactive = izactive;
        this.usersId = usersId;
    }

    public Long getUsersId() {
        return usersId;
    }

    public void setUsersId(Long userId) {
        this.usersId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getIzactive() {
        return izactive;
    }

    public void setIzactive(String izactive) {
        this.izactive = izactive;
    }

    @Override
    public String toString() {
        return "Users{" +
                "usersId='" + usersId + '\'' +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", izactive='" + izactive + '\'' +
                '}';
    }
}
