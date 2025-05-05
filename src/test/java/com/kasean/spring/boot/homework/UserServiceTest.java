package com.kasean.spring.boot.homework;

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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void init() {
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    void testCreateUser() {
        CreateUserRequest request = new CreateUserRequest();
        request.setName("John Doe");

        User user = new User(request);
        user.setId(UUID.randomUUID());

        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.createUser(request);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("John Doe");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testGetUser() {
        UUID id = UUID.randomUUID();
        User user = new User();
        user.setId(id);
        user.setName("John Doe");

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        User result = userService.getUser(id);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("John Doe");
        verify(userRepository, times(1)).findById(id);
    }

    @Test
    void testUpdateUser() {
        UUID id = UUID.randomUUID();
        User user = new User();
        user.setId(id);
        user.setName("John Doe");

        UpdateUserRequest request = new UpdateUserRequest();
        request.setNewName("Jane Doe");

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.updateUser(id, request);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Jane Doe");
        verify(userRepository, times(1)).findById(id);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testDeleteUser() {
        UUID id = UUID.randomUUID();
        User user = new User();
        user.setId(id);
        user.setName("John Doe");

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        User result = userService.deleteUser(id);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("John Doe");
        verify(userRepository, times(1)).findById(id);
        verify(userRepository, times(1)).delete(user);
    }
}
