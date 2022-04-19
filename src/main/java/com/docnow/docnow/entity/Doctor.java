package com.docnow.docnow.entity;

import javax.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

 @Entity
@Table(name="doctors")
public class Doctor {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
     private int id;

     @Column(name="name")
      private String name;

    @Column(name="age")
      private int age;

    @Column(name="speciality")
     private String speciality;

    @Column(name="hospital")
      private String hospital;

 }
