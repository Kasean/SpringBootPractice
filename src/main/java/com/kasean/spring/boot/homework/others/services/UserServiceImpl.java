package com.kasean.spring.boot.homework.others.services;

import com.kasean.spring.boot.homework.others.controllers.models.CreateUserRequest;
import com.kasean.spring.boot.homework.others.controllers.models.UpdateUserRequest;
import com.kasean.spring.boot.homework.others.models.User;
import com.kasean.spring.boot.homework.others.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserServiceImpl implements UserService{

    private static final Logger LOGGER = LoggerFactory.getLogger("UserService");

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(CreateUserRequest request) {
        var user = new User(request);
        return userRepository.save(user);
    }

    @Override
    public User getUser(UUID id) {
        var userOptional = userRepository.findById(id);
        return userOptional.orElse(null);

    }

    @Override
    public User updateUser(UUID id, UpdateUserRequest request) {
        var userOptional = userRepository.findById(id);
        if (userOptional.isEmpty())
            return null;

        var user = userOptional.get();
        user.setName(request.getNewName());
        userRepository.save(user);
        LOGGER.info("User {} old name updated to {}.", id, request.getNewName());
        return user;
    }

    @Override
    public User deleteUser(UUID id) {
        var userOptional = userRepository.findById(id);
        if (userOptional.isEmpty())
            return null;

        userRepository.delete(userOptional.get());
        return userOptional.get();
    }
}
