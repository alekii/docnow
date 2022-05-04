package com.docnow.docnow.auth.controller;

import com.docnow.docnow.auth.entity.Role;
import com.docnow.docnow.auth.entity.RolesEnum;
import com.docnow.docnow.auth.entity.UserData;
import com.docnow.docnow.auth.repository.UserRepository;
import com.docnow.docnow.auth.repository.RolesRepository;
import com.docnow.docnow.auth.request.SignupRequest;
import com.docnow.docnow.auth.response.MessageRes;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path="api/auth")
public class SignUp {
    private final UserRepository userRepository;
    private final RolesRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public SignUp(UserRepository userRepository, RolesRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest request) {

        UserData user = new UserData(request.getUsername(), passwordEncoder.encode(request.getPassword()));

        List<String> rolefromRequest = request.getRole();
        List<Role> roles = new ArrayList<>();
        for(String role : rolefromRequest){
             switch(role){
                 case "admin":
                     if (userRepository.existsByUsername(request.getUsername())) {
                     return ResponseEntity.badRequest()
                             .body(new MessageRes("username exists"));
                 }
                     Role adminRole = roleRepository.findByRolename(RolesEnum.ADMIN)
                             .orElseThrow(() -> new RuntimeException("error"));
                     roles.add(adminRole);
                     break;

                 case "doctor":
                     Role doctorRole = roleRepository.findByRolename(RolesEnum.DOCTOR)
                             .orElseThrow(() -> new RuntimeException("error"));
                     roles.add(doctorRole);
                     break;
                 default:
                 case "patient":
                     Role patientRole = roleRepository.findByRolename(RolesEnum.PATIENT)
                             .orElseThrow(() -> new RuntimeException("error"));
                     roles.add(patientRole);
             }
        }

        user.setRole(roles);
        userRepository.save(user);
        return ResponseEntity.ok().body(new MessageRes("Sign Up Success"));
    }
}
