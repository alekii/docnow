package com.docnow.docnow.domain.repository;

import com.docnow.docnow.domain.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//JPARepository implements all our Crud Features
public interface DoctorRepository extends JpaRepository<Doctor, Long>{
 Optional<Doctor> findByUsername(String username);
}
