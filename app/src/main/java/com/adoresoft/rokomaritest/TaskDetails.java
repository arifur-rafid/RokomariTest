package com.adoresoft.rokomaritest;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TaskDetails {
    private int id;
    private String name;
    private String description;
    private String createdDate;
    private String deadline;
    private String status;
    private String email;
    private String phone;
    private String url;

    public TaskDetails(int id, String name, String description, String createdDate, String deadline, String status, String email, String phone, String url) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdDate = createdDate;
        this.deadline = deadline;
        this.status = status;
        this.email = email;
        this.phone = phone;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
