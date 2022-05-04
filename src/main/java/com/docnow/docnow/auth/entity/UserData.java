package com.docnow.docnow.auth.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor

    @Entity
    @Table(name="users",
    uniqueConstraints = {
            @UniqueConstraint(columnNames="username")
    })
    public class UserData {
    public UserData(String username, String password) {
        this.username = username;
        this.password = password;
    }

        @Id
        @GeneratedValue(strategy= GenerationType.IDENTITY)
        @Column(name="id")
        private int id;

        @Column(name="username")
        @NotBlank
        @Size(max=20)
        private String username;

        @JsonIgnore
        @NotBlank
        @Size(max=120)
        @Column(name="password")
        private String password;

        @ManyToMany(
                cascade = { CascadeType.PERSIST, CascadeType.MERGE },
                fetch=FetchType.LAZY
        )

        @JoinTable(
                name ="user_roles",
                joinColumns=@JoinColumn(name="user_id"),
                inverseJoinColumns=@JoinColumn(name="role_id")
                )

        @Column(name="roles")
        private List<Role> role = new ArrayList<>();

    }


