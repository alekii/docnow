package com.docnow.docnow.entity;

import javax.persistence.*;

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

  public Doctor(){

  }
    public Doctor(String name, int age, String speciality, String hospital) {
        this.name = name;
        this.age = age;
        this.speciality = speciality;
        this.hospital = hospital;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getSpeciality() {
        return speciality;
    }

    public String getHospital() {
        return hospital;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

     public int getId() {
         return id;
     }

     public void setId(int id) {
         this.id = id;
     }

     @Override
    public String toString() {
        return "Doctor{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", speciality='" + speciality + '\'' +
                ", hospital='" + hospital + '\'' +
                '}';
    }

 }
