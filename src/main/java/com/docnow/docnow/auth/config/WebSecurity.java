package com.docnow.docnow.auth.config;

import com.docnow.docnow.auth.jwt.JWTFilter;
import com.docnow.docnow.auth.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;


@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Bean
    public JWTFilter jwtFilter(){
        return new JWTFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
       return new BCryptPasswordEncoder();
   }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
         auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    //expose authentication manager for implementing in login/signup as it is not publicly accessible
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                //set session management to stateless
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                //set authentication error handler
                .and()
                .exceptionHandling().authenticationEntryPoint((request, response, authException) -> {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Check your details");
                });
        //set endpoints
        http.authorizeRequests().antMatchers("/api/auth/**").permitAll()
                .antMatchers("/docnowchat/**").authenticated()
                .antMatchers("/admin/**").hasAuthority("ADMIN");

        //Check JWT token
        http.addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
     }
}
