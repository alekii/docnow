package com.docnow.docnow.domain.request;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DoctorRequest {
    private String username;
    private String name;
    private int age;
    private String speciality;
    private String hospital;

    public DoctorRequest(String username){
        this.username = username;
    }
}
