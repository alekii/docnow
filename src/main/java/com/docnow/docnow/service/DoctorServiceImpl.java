package com.docnow.docnow.service;

import com.docnow.docnow.dao.DoctorRepository;
import com.docnow.docnow.entity.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorServiceImpl implements DoctorService{

    private final DoctorRepository doctorRepository;

    @Autowired
    public DoctorServiceImpl(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public List<Doctor> findAll() {
         return doctorRepository.findAll();
    }

    @Override
    public Doctor findById(int theId) {
        Optional<Doctor> result = doctorRepository.findById(theId);
        Doctor theDoctor;
        if (result.isPresent()){
            theDoctor = result.get();
        }
        else {
            throw new RuntimeException("Did not find id " +theId);
        }
        return theDoctor;

    }

    @Override
    public void save(Doctor theDoctor) {
        doctorRepository.save(theDoctor);
    }

    @Override
    public void deleteById(int theId) {
        doctorRepository.deleteById(theId);
    }
}
