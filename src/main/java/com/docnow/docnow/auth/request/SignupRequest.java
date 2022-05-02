package com.docnow.docnow.auth.request;
import lombok .*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor

public class SignupRequest {
    private String username;
    private String password;
    private List<String> role;
}
