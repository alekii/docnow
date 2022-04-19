package com.docnow.docnow.dao;

import com.docnow.docnow.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path="users")
public interface PatientRepository extends JpaRepository<Patient, Integer>{
}
