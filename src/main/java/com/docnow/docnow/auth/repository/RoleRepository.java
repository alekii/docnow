package com.docnow.docnow.auth.repository;

import com.docnow.docnow.auth.entity.Role;
import com.docnow.docnow.auth.entity.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Integer> {
    Optional<Role> findByRolename(RoleEnum name);
}
