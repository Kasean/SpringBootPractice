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
        return userRepository.findByEmail(request.getEmail()).orElse(userRepository.save(new User(request)));
    }

    @Override
    public User getUser(UUID id) {
        return userRepository.findById(id).orElse(null);

    }

    @Override
    public User updateUser(UUID id, UpdateUserRequest request) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setName(request.getNewName());
                    LOGGER.info("User {} old name updated to {}.", id, request.getNewName());
                    return userRepository.save(user);
                }).orElse(null);
    }

    @Override
    public User deleteUser(UUID id) {
        return userRepository.findById(id).map(user -> {
            userRepository.delete(user);
            return user;
        }).orElse(null);
    }
}
