package com.docnow.docnow.auth.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.docnow.docnow.auth.services.UserDetailsServiceImpl;

import java.io.IOException;

public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Bean
    public JwtUtil jwtUtil(){
        return new JwtUtil();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
           try{
               //get jwt token from request header
               String AuthToken = request.getHeader("Authorization");
              if(AuthToken!=null && AuthToken.startsWith("Bearer ")) {
                  final String jwt = AuthToken.split(" ")[1].trim();
                  //get user identity
                  UserDetails userDetails = userDetailsService.loadUserByUsername(jwtUtil().generateUsernameFromToken((jwt)));
                  //set user identity in security context
                  UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                  authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                  SecurityContextHolder.getContext().setAuthentication(authentication);
              }
           }catch(Exception e){
               e.getStackTrace();
           }
           filterChain.doFilter(request,response);
    }
}
