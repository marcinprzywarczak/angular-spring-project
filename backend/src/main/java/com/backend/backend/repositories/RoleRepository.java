package com.backend.backend.repositories;

import com.backend.backend.models.ERole;
import com.backend.backend.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}