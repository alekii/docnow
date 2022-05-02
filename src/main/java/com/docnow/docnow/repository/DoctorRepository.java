package com.docnow.docnow.repository;

import com.docnow.docnow.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

//JPARepository implements all our Crud Features
public interface DoctorRepository extends JpaRepository<Doctor, Integer>{

}
