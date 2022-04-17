package com.docnow.docnow.dao;

import com.docnow.docnow.entity.Doctor;

import java.util.List;

public interface DoctorDAO {

    public List<Doctor> findAll();

    public Doctor findById(int theId);

    public void save(Doctor theDoctor);

    public void deleteById(int theId);

}
