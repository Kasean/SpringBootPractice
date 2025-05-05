package com.kasean.spring.boot.homework.second.services;

import com.kasean.spring.boot.homework.second.controllers.models.CreateUserRequest;
import com.kasean.spring.boot.homework.second.controllers.models.UpdateUserRequest;
import com.kasean.spring.boot.homework.second.models.User;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface UserService {

    User createUser(CreateUserRequest request);

    User getUser(UUID id);

    User updateUser(UUID id, UpdateUserRequest request);

    User deleteUser(UUID id);
}
