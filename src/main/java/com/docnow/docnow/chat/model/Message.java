package com.docnow.docnow.chat.model;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private String senderName;
    private  String receiverName;
    private String message;
}
