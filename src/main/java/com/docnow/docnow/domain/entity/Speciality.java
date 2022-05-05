package com.docnow.docnow.domain.entity;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import lombok.*;
import org.hibernate.annotations.NaturalId;

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
    @NaturalId
    private String name;
    @ManyToMany(mappedBy = "speciality")
    @Column(name = "doctor")
    private final Set<Doctor> doctor = new LinkedHashSet<>();

    public Speciality(String s) {
        this.name=s;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)  return true;
        if (!(o instanceof Speciality)) return false;
        Speciality s = (Speciality) o;
        return Objects.equals(name, s.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
