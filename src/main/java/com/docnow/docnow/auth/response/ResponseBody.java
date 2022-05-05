package com.docnow.docnow.auth.response;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class ResponseBody {
    private String username;
    private List<String> Roles;
}
