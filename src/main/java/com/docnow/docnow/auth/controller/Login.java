package com.docnow.docnow.auth.controller;

import com.docnow.docnow.auth.jwt.JwtUtil;
import com.docnow.docnow.auth.request.LoginRequest;
import com.docnow.docnow.auth.response.MessageRes;
import com.docnow.docnow.auth.response.ResBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path="api/auth")
public class Login {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Bean
    public JwtUtil jwtUtil(){
        return new JwtUtil();
    }
   @PostMapping("/login")
   public ResponseEntity<?> login(@RequestBody LoginRequest request) {
       try {
           Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
           String jwt = jwtUtil().generateJwt(authentication);
           User user = (User) authentication.getPrincipal();
           SecurityContextHolder.getContext().setAuthentication(authentication);
           List<String> role=user.getAuthorities()
                   .stream()
                   .map(GrantedAuthority::getAuthority)
                   .collect(Collectors.toList());

           return  ResponseEntity.ok()
                   .header("Authorization",jwt)
                   .body(new ResBody(user.getUsername(),role));

       } catch (BadCredentialsException ex) {
           return ResponseEntity.badRequest().body(new MessageRes("Unauthorized"));
       }
   }
}
