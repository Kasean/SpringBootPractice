package com.kasean.spring.boot.homework;

import com.kasean.spring.boot.homework.others.models.User;
import com.kasean.spring.boot.homework.others.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void testCreateAndFindUser() {

        var user = new User();
        user.setName("John Doe");
        User savedUser = userRepository.save(user);

        var foundUser = userRepository.findById(savedUser.getId()).orElse(null);
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getName()).isEqualTo("John Doe");
    }

    @Test
    void testUpdateUser() {

        var user = new User();
        user.setName("John Doe");
        var savedUser = userRepository.save(user);

        savedUser.setName("Jane Doe");
        userRepository.save(savedUser);

        var foundUser = userRepository.findById(savedUser.getId()).orElse(null);
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getName()).isEqualTo("Jane Doe");
    }

    @Test
    void testDeleteUser() {

        var user = new User();
        user.setName("John Doe");
        var savedUser = userRepository.save(user);

        userRepository.deleteById(savedUser.getId());

        var foundUser = userRepository.findById(savedUser.getId()).orElse(null);
        assertThat(foundUser).isNull();
    }
}
