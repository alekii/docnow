package com.docnow.docnow.chat.Entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name="chatUsers")
public class ChatUser {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @NotBlank
    @Size(max=256)
    @Column(name="sender_id")
    private String from_id;
    @NotBlank
    @Column(name="recipient_id")
    private String to_id;
    @Column(name="last_message")
    private String lastMessage;
    @Column(name="last_seen")
    private Date lastSeen;

    @OneToMany(mappedBy = "chatUsers",
                              cascade = CascadeType.ALL,
                               orphanRemoval = true)
    private List<Chat> chats = new ArrayList<>();

    public ChatUser(String sender_id, String receiver_id) {
        this.from_id =sender_id;
        this.to_id=receiver_id;
    }

    //add methods to ensure synchronization
     public void addChat(Chat chat){
        chats.add(chat);
        chat.setChatUser(this);
     }

}
