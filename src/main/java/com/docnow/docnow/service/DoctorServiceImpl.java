package com.docnow.docnow.service;

import com.docnow.docnow.dao.DoctorDAO;
import com.docnow.docnow.entity.Doctor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService{

    private DoctorDAO doctorDAO;

    public DoctorServiceImpl(DoctorDAO doctorDAO) {
        this.doctorDAO = doctorDAO;
    }

    @Override
    @Transactional
    public List<Doctor> findAll() {
         return doctorDAO.findAll();
    }

    @Override
    @Transactional
    public Doctor findById(int theId) {
        return doctorDAO.findById(theId);
    }

    @Override
    @Transactional
    public void save(Doctor theDoctor) {
        doctorDAO.save(theDoctor);
    }

    @Override
    @Transactional
    public void deleteById(int theId) {
        doctorDAO.deleteById(theId);
    }
}
