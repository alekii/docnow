package com.docnow.docnow.domain.request;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserRequest {
    private String username;
    private String first_name;
    private String last_name;
    private int age;
    private String gender;
    private String county;
    private String town;


    public UserRequest(String username){

        this.username = username;
    }
}
