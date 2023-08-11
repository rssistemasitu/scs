package com.rogerio.servicesecurity.repository;

import com.rogerio.servicesecurity.entity.Role;
import com.rogerio.servicesecurity.enums.ERole;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(ERole name);
}
