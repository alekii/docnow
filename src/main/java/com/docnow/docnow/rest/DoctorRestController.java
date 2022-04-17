package com.docnow.docnow.rest;

import com.docnow.docnow.entity.Doctor;
import com.docnow.docnow.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DoctorRestController {

    private final DoctorService doctorService;

    @Autowired
    public DoctorRestController(DoctorService thedoctorService) {
        doctorService = thedoctorService;

    }

    @GetMapping("/doctors")
    public List<Doctor> findAll(){
        return doctorService.findAll();
    }

    @GetMapping("/doctors/{doctorId}")
    public Doctor getDoctor(@PathVariable int doctorId){
        Doctor theDoctor = doctorService.findById(doctorId);
        if(theDoctor==null){
            throw new RuntimeException("Doctor not found- " +doctorId);
        }
        return theDoctor;
     }

     @PostMapping("/doctors")
    public Doctor addDoctor(@RequestBody Doctor theDoctor){
        theDoctor.setId(0);
        doctorService.save(theDoctor);
        return theDoctor;
     }


    @PutMapping("/doctors/")
    public Doctor updateDoctor(@RequestBody Doctor theDoctor){
        doctorService.save(theDoctor);
        return theDoctor;
    }

    @DeleteMapping("/doctors/{doctorId}")
    public String removeDoctor(@PathVariable int doctorId){
        Doctor theDoctor = doctorService.findById(doctorId);
        if(theDoctor==null){
            return "Doctor with Id "+ doctorId +" doesn't exist";
        }
        doctorService.deleteById(doctorId);
        return "Success";
    }



}
