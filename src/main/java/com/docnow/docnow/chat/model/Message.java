package com.docnow.docnow.chat.model;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private int sender_id;
    private  int receiver_id;
    private String message;
    private Status status;
    private Date timeSent;
}
