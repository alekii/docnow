package com.docnow.docnow.domain.entity;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Entity
@Table(name="speciality")
public class Speciality {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long Id;
    @Column(name="name")
    private String name;
    @ManyToMany(mappedBy = "speciality")
    @Column(name = "doctor")
    private final Set<Doctor> doctor = new LinkedHashSet<>();

    public Speciality(String s) {
        this.name=s;
    }
}
