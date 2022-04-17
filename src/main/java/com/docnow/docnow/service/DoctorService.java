package com.docnow.docnow.service;

import com.docnow.docnow.entity.Doctor;

import java.util.List;

public interface DoctorService {

    public List<Doctor> findAll();

    public Doctor findById(int theId);

    public void save(Doctor theDoctor);

    public void deleteById(int theId);
}
