package com.taskmanager.demo.db;

import jakarta.persistence.*;

import java.time.ZoneId;
import java.util.Set;

@Entity
@Table(name = "Users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long usersId;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    ZoneId defaultZoneId = ZoneId.systemDefault();
    @Column(name = "time_zone")
    private String timeZone = defaultZoneId.getId();
    @Column(name = "is_active")
    private String izactive;

    @OneToMany(mappedBy = "assignedTo", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<Task> tasks; // List of tasks assigned to the user

    public Users() {
    }

    public Users(Long usersId, String firstName, String lastName, ZoneId defaultZoneId, String timeZone, String izactive) {
        this.usersId = usersId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.defaultZoneId = defaultZoneId;
        this.timeZone = timeZone;
        this.izactive = izactive;
    }

    // Getters and Setters

    public Long getUsersId() {
        return usersId;
    }

    public void setUsersId(Long id) {
        this.usersId = id;
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

    public ZoneId getDefaultZoneId() {
        return defaultZoneId;
    }

    public void setDefaultZoneId(ZoneId defaultZoneId) {
        this.defaultZoneId = defaultZoneId;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getIzactive() {
        return izactive;
    }

    public void setIzactive(String izactive) {
        this.izactive = izactive;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        return "User{" +
                "usersId=" + usersId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", defaultZoneId=" + defaultZoneId +
                ", timeZone='" + timeZone + '\'' +
                ", izactive='" + izactive + '\'' +
                ", tasks=" + tasks +
                '}';
    }
}