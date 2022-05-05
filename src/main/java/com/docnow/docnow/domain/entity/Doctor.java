package com.docnow.docnow.domain.entity;

import javax.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

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
     private Long id;

     @Column(name="name")
      private String name;

    @Column(name="username")
    private String username;

    @Column(name="age")
      private int age;

    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinTable(name="doctor_speciality",
            joinColumns = @JoinColumn(name="doctor_id"),
            inverseJoinColumns = @JoinColumn(name="speciality_id"))
    //use set instead of list for efficiency(from database perspective)
     private Set<Speciality> speciality = new LinkedHashSet<>();

    @Column(name="hospital")
      private String hospital;

    public Doctor(String name, int age, Set<Speciality> speciality,String hospital) {
      this.name = name;
      this.age=age;
      this.speciality = speciality;
      this.hospital=hospital;
    }



    //use this to add or remove item since we used Set
    //To maintain sync between doctor and speciality entities
    public void addSpeciality(Speciality specialityArg){
        speciality.add(specialityArg);
        specialityArg.getDoctor().add(this);
    }

    public void removeSpeciality(Speciality specialityArg){
        speciality.remove(specialityArg);
        specialityArg.getDoctor().remove(this);
    }

    @Override
    public boolean equals(Object obj) {
        if(this==obj){
            return true;
        }
        if(!(getClass() == obj.getClass())){
            return false;
        }
        // int cannot work here. id is of type Long
        return id !=null && id.equals(((Doctor)obj).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
