package com.kasean.spring.boot.homework;

import com.kasean.spring.boot.homework.others.controllers.UserController;
import com.kasean.spring.boot.homework.others.controllers.UserControllerImpl;
import com.kasean.spring.boot.homework.others.controllers.models.CreateUserRequest;
import com.kasean.spring.boot.homework.others.controllers.models.UpdateUserRequest;
import com.kasean.spring.boot.homework.others.models.User;
import com.kasean.spring.boot.homework.others.repositories.UserRepository;
import com.kasean.spring.boot.homework.others.services.UserService;
import com.kasean.spring.boot.homework.others.services.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerIntegrationTest {

    private UserController userController;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void init() {
        UserService userService = new UserServiceImpl(userRepository);
        userController = new UserControllerImpl(userService);
    }

    @Test
    void testCreateUser() {
        CreateUserRequest request = new CreateUserRequest();
        request.setName("John Doe");

        User user = new User();
        user.setId(UUID.randomUUID());
        user.setName("John Doe");

        when(userRepository.save(any(User.class))).thenReturn(user);

        userController.createUser(request);

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testGetUser() {
        User user = new User();
        UUID id = UUID.randomUUID();
        user.setId(id);
        user.setName("John Doe");

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        userController.getUser(id);

        verify(userRepository, times(1)).findById(id);
    }

    @Test
    void testUpdateUser() {
        User user = new User();
        UUID id = UUID.randomUUID();
        user.setId(id);
        user.setName("John Doe");

        UpdateUserRequest request = new UpdateUserRequest();
        request.setNewName("Jane Doe");

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        userController.updateUser(id, request);

        verify(userRepository, times(1)).findById(id);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testDeleteUser() {
        User user = new User();
        UUID id = UUID.randomUUID();
        user.setId(id);
        user.setName("John Doe");

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        userController.deleteUser(id);

        verify(userRepository, times(1)).findById(id);
        verify(userRepository, times(1)).delete(any(User.class));
    }
}
