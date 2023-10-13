package com.example.socialplatform.repository;

import com.example.socialplatform.schema.ERole;
import com.example.socialplatform.schema.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(ERole name);
}
