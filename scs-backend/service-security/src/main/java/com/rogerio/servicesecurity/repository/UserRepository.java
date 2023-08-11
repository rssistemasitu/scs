package com.rogerio.servicesecurity.repository;

import com.rogerio.servicesecurity.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);
    Optional<User> findByResetPasswordToken(String token);
    Page<User> findByStatus(boolean active, Pageable pageable);
}
