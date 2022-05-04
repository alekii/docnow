package com.docnow.docnow.domain.entity;

import javax.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name="patients")
public class Patient {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="first_name")
    private String first_name;

    @Column(name="last_name")
    private String last_name;

    @Column(name="username")
    private String username;

    @Column(name="gender")
    private String gender;

    @Column(name="age")
    private int age;

    @Column(name="county")
    private String county;

    @Column(name="town")
    private String town;

    public Patient(String username, String first_name, String last_name, int age, String gender, String county, String town) {
        this.username = username;
        this.first_name = first_name;
        this.last_name = last_name;
        this.gender= gender;
        this.county = county;
         this.town= town;
    }
}
