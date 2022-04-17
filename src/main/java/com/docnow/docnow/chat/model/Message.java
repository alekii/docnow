package com.docnow.docnow.chat.model;

import lombok.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private String senderName;
    private  String receiverName;
    private String message;
    private String date;
    private String status;
}
