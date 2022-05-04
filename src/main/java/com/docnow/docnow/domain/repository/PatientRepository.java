package com.docnow.docnow.domain.repository;

import com.docnow.docnow.domain.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(path="users")
public interface PatientRepository extends JpaRepository<Patient, Integer>{
    Optional<Patient> findByUsername(String username);
}
