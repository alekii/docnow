package com.docnow.docnow.auth.controller;

import com.docnow.docnow.auth.entity.Role;
import com.docnow.docnow.auth.entity.RoleEnum;
import com.docnow.docnow.auth.entity.UserData;
import com.docnow.docnow.auth.repository.UserRepository;
import com.docnow.docnow.auth.repository.RoleRepository;
import com.docnow.docnow.auth.request.SignupRequest;
import com.docnow.docnow.auth.response.MessageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(path="api/auth")
public class SignUp {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public SignUp(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest request) {

        UserData user = new UserData(request.getUsername(), passwordEncoder.encode(request.getPassword()));

        List<String> rolefromRequest = request.getRole();
        Set<Role> roles = new LinkedHashSet<>() {
        };
        for(String role : rolefromRequest){
             switch(role){
                 case "admin":
                     if (userRepository.existsByUsername(request.getUsername())) {
                     return ResponseEntity.badRequest()
                             .body(new MessageResponse("username exists"));
                 }
                     Role adminRole = roleRepository.findByRolename(RoleEnum.ADMIN)
                             .orElseThrow(() -> new RuntimeException("error"));
                     roles.add(adminRole);
                     break;

                 case "doctor":
                     Role doctorRole = roleRepository.findByRolename(RoleEnum.DOCTOR)
                             .orElseThrow(() -> new RuntimeException("error"));
                     roles.add(doctorRole);
                     break;
                 default:
                 case "patient":
                     Role patientRole = roleRepository.findByRolename(RoleEnum.PATIENT)
                             .orElseThrow(() -> new RuntimeException("error"));
                     roles.add(patientRole);
             }
        }

        user.setRole(roles);
        userRepository.save(user);
        return ResponseEntity.ok().body(new MessageResponse("Sign Up Success"));
    }
}
