package com.kasean.spring.boot.homework.second.controllers;

import com.kasean.spring.boot.homework.second.controllers.models.CreateUserRequest;
import com.kasean.spring.boot.homework.second.controllers.models.UpdateUserRequest;
import com.kasean.spring.boot.homework.second.models.User;
import org.hibernate.sql.Update;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1/user")
public interface UserController{

    @PostMapping("/create")
    User createUser(@RequestBody CreateUserRequest request);

    @GetMapping("/{id}")
    User getUser(@PathVariable UUID id);

    @PutMapping("/{id}/update")
    User updateUser(@PathVariable UUID id, @RequestBody UpdateUserRequest request);

    @DeleteMapping("/{id}")
    User deleteUser(@PathVariable UUID id);

}
