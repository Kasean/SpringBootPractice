package com.kasean.spring.boot.homework.others.controllers;

import com.kasean.spring.boot.homework.others.controllers.models.CreateUserRequest;
import com.kasean.spring.boot.homework.others.controllers.models.UpdateUserRequest;
import com.kasean.spring.boot.homework.others.models.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1/user")
public interface UserController{

    @PostMapping("/create")
    User createUser(@RequestBody CreateUserRequest request);

    @GetMapping("/{id}")
    User getUser(@PathVariable UUID id, @AuthenticationPrincipal OAuth2User jwt);

    @PutMapping("/{id}/update")
    User updateUser(@PathVariable UUID id, @RequestBody UpdateUserRequest request, @AuthenticationPrincipal OAuth2User jwt);

    @DeleteMapping("/{id}")
    User deleteUser(@PathVariable UUID id);

    @GetMapping("/me")
    User getCurrentUser(@AuthenticationPrincipal OAuth2User jwt);

}
