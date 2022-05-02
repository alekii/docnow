package com.docnow.docnow.auth.repository;

import com.docnow.docnow.auth.entity.Role;
import com.docnow.docnow.auth.entity.RolesEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolesRepository extends JpaRepository<Role,Integer> {
    Optional<Role> findByRolename(RolesEnum name);
}
