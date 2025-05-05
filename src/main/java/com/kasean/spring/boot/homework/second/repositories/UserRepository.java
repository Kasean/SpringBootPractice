package com.kasean.spring.boot.homework.second.repositories;

import com.kasean.spring.boot.homework.second.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
}
