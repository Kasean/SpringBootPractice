package com.kasean.spring.boot.homework.others.repositories;

import com.kasean.spring.boot.homework.others.models.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RequestRepository extends JpaRepository<Request, UUID> {
}
