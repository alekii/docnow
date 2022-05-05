package com.docnow.docnow.auth.services;


import com.docnow.docnow.auth.entity.Role;
import com.docnow.docnow.auth.entity.UserData;
import com.docnow.docnow.auth.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
   @Autowired
   private UserRepository userRepository;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserData userdata= userRepository.findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("username could not be found"));
       //Now build userDetails, Add Granted authority
        return  new User(userdata.getUsername(),userdata.getPassword(),
                                     setAuthority(userdata.getRole()));
    }

    private Collection<? extends GrantedAuthority> setAuthority(Set<Role> role){
        return role.stream()
                .map(r->new SimpleGrantedAuthority(r.getRolename().name()))
                .collect(Collectors.toList());
    }
}
