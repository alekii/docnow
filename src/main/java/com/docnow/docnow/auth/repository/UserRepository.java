package com.docnow.docnow.auth.repository;

import com.docnow.docnow.auth.entity.UserData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserData,Long> {
    Boolean existsByUsername(String username) ;
    Optional<UserData> findByUsername(String username);
}
