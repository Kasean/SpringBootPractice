package com.kasean.spring.boot.homework.others.models;

import com.kasean.spring.boot.homework.others.controllers.models.CreateUserRequest;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "users")
public class User {

    @Id
    private UUID id = UUID.randomUUID();

    private String name;

    public User() {
    }

    public User(CreateUserRequest request) {
        this.name = request.getName();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
