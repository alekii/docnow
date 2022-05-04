package com.docnow.docnow.chat.Entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name="chat_users")
public class ChatUsers {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @NotBlank
    @Size(max=256)
    @Column(name="sender_id")
    private int from_id;
    @NotBlank
    @Column(name="recipient_id")
    private int to_id;
    @Column(name="last_message")
    private String lastMessage;
    @Column(name="last_seen")
    private Date lastSeen;

    public ChatUsers(int sender_id, int receiver_id) {
        this.from_id =sender_id;
        this.to_id=receiver_id;
    }
}
