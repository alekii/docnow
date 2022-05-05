package com.docnow.docnow.auth.controller;

import com.docnow.docnow.auth.jwt.JwtUtil;
import com.docnow.docnow.auth.request.LoginRequest;
import com.docnow.docnow.auth.response.MessageResponse;
import com.docnow.docnow.auth.response.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path="api/auth")
public class Login {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;
   @PostMapping("/login")
   public ResponseEntity<?> login(@RequestBody LoginRequest request) {
       try {
           Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
           String jwt = jwtUtil.generateJwt(authentication);
           User user = (User) authentication.getPrincipal();
           SecurityContextHolder.getContext().setAuthentication(authentication);
           List<String> role= new ArrayList<>();
           user.getAuthorities().forEach(grantedAuthority->{
               role.add(grantedAuthority.getAuthority());
           });
           return  ResponseEntity.ok()
                   .header("Authorization",jwt)
                   .body(new ResponseBody(user.getUsername(),role));
       } catch (BadCredentialsException ex) {
           return ResponseEntity.badRequest().body(new MessageResponse("Unauthorized"));
       }
   }
}
