package com.rogerio.servicesecurity.service;

import com.rogerio.servicesecurity.entity.Role;
import com.rogerio.servicesecurity.enums.ERole;
import com.rogerio.servicesecurity.exception.error.NotFoundException;
import com.rogerio.servicesecurity.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.rogerio.servicesecurity.utils.Constants.NOT_FOUND_EXCEPTION_MESSAGE;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Cacheable("roles")
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Cacheable("role-id")
    public Role getById(String id) {
        return roleRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_EXCEPTION_MESSAGE,"Função", "id", id)));
    }

    @Cacheable("role-name")
    public Role getByName(ERole name) {
        return roleRepository.findByName(name).orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_EXCEPTION_MESSAGE,"Função", "nome", name)));
    }

    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }
}
