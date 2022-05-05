package com.docnow.docnow.domain.controller;

import com.docnow.docnow.domain.entity.Patient;
import com.docnow.docnow.domain.repository.PatientRepository;
import com.docnow.docnow.domain.request.UserRequest;
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

public class PatientController {
    @Autowired
    private PatientRepository patientRepository;

    @GetMapping("users")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Patient> getPatients(){
        return patientRepository.findAll();
    }

    @PostMapping("/users/add")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('PATIENT')")
        public ResponseEntity<?> addUser(@RequestBody UserRequest request){
        Patient user = new Patient(request.getUsername(),request.getFirst_name(), request.getLast_name(),
                                                       request.getAge(),request.getGender(),request.getCounty(),request.getTown());
        patientRepository.save(user);
        return ResponseEntity.ok()
                .body(new MessageResponse( "User added Successfully"));
    }

    @PostMapping("/users/find")
    @PreAuthorize("hasAuthority('ADMIN')" )
    public ResponseEntity<?> findUser(@RequestBody UserRequest request){
        Optional<Patient> user = patientRepository.findByUsername(request.getUsername());
        if(user.isPresent()){
            return ResponseEntity.ok()
                    .body(user);
        } else{
            return ResponseEntity.badRequest()
                    .body(new MessageResponse( "User does not exist"));
        }
    }
    @PutMapping("/users/update")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('PATIENT')" )
    public ResponseEntity<?>  updatedUser(@RequestBody UserRequest request){
        Patient user = patientRepository.findByUsername(request.getUsername())
                .orElseThrow(()->new ResourceNotFoundException("user not found"));
        user.setFirst_name(request.getFirst_name());
        user.setAge(request.getAge());
        user.setGender(request.getGender());
        user.setCounty(request.getCounty());
        user.setTown(request.getTown());
        patientRepository.save(user);
        return ResponseEntity.ok()
                .body(user);
    }

    @DeleteMapping("/users/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable Long id)  {
        try{  patientRepository.deleteById(id);
            return ResponseEntity.ok()
                    .body( new MessageResponse(String.format("User with id %s has been deleted",id)));
        }
        catch(DataAccessException ex){
            System.out.println("id does not exist");
        }
        return ResponseEntity.badRequest()
                .body(new MessageResponse( "User does not exist"));
    }
}
