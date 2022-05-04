package com.docnow.docnow.domain.controller;

import com.docnow.docnow.domain.entity.Doctor;
import com.docnow.docnow.domain.repository.DoctorRepository;
import com.docnow.docnow.domain.request.DoctorRequest;
import com.docnow.docnow.domain.response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/")

public class DoctorController {
    @Autowired
    private DoctorRepository doctorRepository;

    @GetMapping("doctors")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('PATIENT') or hasAuthority('DOCTOR')")
    public List<Doctor> getDoctors(){
        return doctorRepository.findAll();
    }

    @PostMapping("/doctors/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> addDoctor(@RequestBody DoctorRequest request){
        Doctor doctor = new Doctor(request.getName(),request.getAge(),request.getSpeciality(),request.getHospital());
        doctorRepository.save(doctor);
            return ResponseEntity.ok()
                    .body(new MessageResponse( "Doctor added Successfully"));
    }

    @PostMapping("/doctors/find")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('DOCTOR') or hasAuthority('PATIENT')")
    public ResponseEntity<?> findDoctor(@RequestBody @NotNull DoctorRequest request){
        Optional<Doctor> doctor = doctorRepository.findByUsername(request.getName());
        if(doctor.isPresent()){
            return ResponseEntity.ok()
                    .body(doctor);
        } else{
            return ResponseEntity.badRequest()
                .body(new MessageResponse( "Doctor does not exist"));
        }
    }
    @PutMapping("/doctors/update")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('DOCTOR')" )
    public ResponseEntity<?>  updatedDoctor(@RequestBody DoctorRequest request){
        Doctor doctor = doctorRepository.findByUsername(request.getName())
                .orElseThrow(()->new ResourceNotFoundException("Doctor not found"));
         doctor.setName(request.getName());
         doctor.setAge(request.getAge());
         doctor.setSpeciality(request.getSpeciality());
         doctor.setHospital(request.getHospital());
        doctorRepository.save(doctor);
            return ResponseEntity.ok()
                    .body(doctor);
    }

    @DeleteMapping("/doctors/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteDoctor(@PathVariable int id)  {
      try{  doctorRepository.deleteById(id);
          return ResponseEntity.ok()
                  .body( new MessageResponse(String.format("Doctor with id %s has been deleted",id)));
      }
      catch(DataAccessException ex){
          System.out.println("id does not exist");
      }
      return ResponseEntity.badRequest()
                .body(new MessageResponse( "Doctor does not exist"));
    }
}
