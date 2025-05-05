package com.kasean.spring.boot.homework.second.controllers.models;

public class UpdateUserRequest {
    private String newName;

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }
}
