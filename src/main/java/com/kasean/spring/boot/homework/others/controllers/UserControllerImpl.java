package com.kasean.spring.boot.homework.others.controllers;

import com.kasean.spring.boot.homework.others.controllers.models.CreateUserRequest;
import com.kasean.spring.boot.homework.others.controllers.models.UpdateUserRequest;
import com.kasean.spring.boot.homework.others.models.User;
import com.kasean.spring.boot.homework.others.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserControllerImpl implements UserController{

    private static final Logger LOGGER = LoggerFactory.getLogger("UserController");

    private final UserService userService;

    public UserControllerImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public User createUser(CreateUserRequest request) {
        LOGGER.info("Creating user with name {}...", request.getName());
        var newUser = userService.createUser(request);
        LOGGER.info("User {} created.", newUser.getName());
        return newUser;
    }

    @Override
    public User getUser(UUID id, OAuth2User jwt) {
        LOGGER.info("Searching user...");
        var user = userService.getUser(id);
        LOGGER.info("User with id {} {}", id, user != null ? "founded." : "not founded.");
        return user;
    }

    @Override
    public User updateUser(UUID id, UpdateUserRequest request, OAuth2User jwt) {
        LOGGER.info("Updating user...");
        var user = userService.updateUser(id, request);
        LOGGER.info("User with id {} {}", id, user != null ? "updated." : "not updated.");
        return user;
    }

    @Override
    public User deleteUser(UUID id) {
        LOGGER.info("Deleting user...");
        var user = userService.deleteUser(id);
        LOGGER.info("User with id {} {}", id, user != null ? "deleted." : "not deleted.");
        return user;
    }

    @Override
    public User getCurrentUser(OAuth2User jwt) {
        var createRequest = new CreateUserRequest();
        createRequest.setName(jwt.getName());
        var newUser = userService.createUser(createRequest);
        LOGGER.info("User {} created.", newUser.getName());
        return newUser;
    }
}
