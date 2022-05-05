package com.docnow.docnow.domain.controller;

import com.docnow.docnow.domain.entity.Doctor;
import com.docnow.docnow.domain.entity.Speciality;
import com.docnow.docnow.domain.repository.DoctorRepository;
import com.docnow.docnow.domain.repository.SpecialityRepository;
import com.docnow.docnow.domain.request.DoctorRequest;
import com.docnow.docnow.domain.request.FindBySpecialityRequest;
import com.docnow.docnow.domain.response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/")

public class DoctorController {
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private SpecialityRepository specialityRepository;

    @GetMapping("doctors")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('PATIENT') or hasAuthority('DOCTOR')")
    public List<Doctor> getDoctors(){
        return doctorRepository.findAll();
    }

    @PostMapping("/doctors/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> addDoctor(@RequestBody DoctorRequest request){
        Set<Speciality>  speciality = SpecialityIntoSet(request.getSpeciality());
        Doctor doctor = new Doctor(request.getName(),request.getAge(),speciality,request.getHospital());
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
       Set<Speciality>  speciality = SpecialityIntoSet(request.getSpeciality());
        Doctor doctor = doctorRepository.findByUsername(request.getName())
                .orElseThrow(()->new ResourceNotFoundException("Doctor not found"));
         doctor.setName(request.getName());
         doctor.setAge(request.getAge());
         doctor.setSpeciality(speciality);
         doctor.setHospital(request.getHospital());
        doctorRepository.save(doctor);
            return ResponseEntity.ok()
                    .body(doctor);
    }

    @DeleteMapping("/doctors/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteDoctor(@PathVariable Long id)  {
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

    private Set<Speciality> SpecialityIntoSet(List<String> specialityListFromRequest) {
        Set<Speciality> speciality = new LinkedHashSet<>();
        for(String s : specialityListFromRequest){
            Speciality newSpeciality = new Speciality(s);
            speciality.add(newSpeciality);
        }
        return speciality;
    }

    public ResponseEntity<?> findDoctorBySpeciality(@RequestBody FindBySpecialityRequest request){
        Optional<Doctor> doctor = doctorRepository.findBySpeciality(request.getIllness());
        if(doctor.isPresent()) return ResponseEntity.ok().body(doctor);
        return ResponseEntity.badRequest().body(new MessageResponse("No Doctor with said Speciality was found"));
    }
}
