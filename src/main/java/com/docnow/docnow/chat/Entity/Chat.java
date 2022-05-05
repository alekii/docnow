package com.docnow.docnow.chat.Entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

import lombok.*;
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name="chats")
public class Chat {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @NotBlank
    @Size(max=256)
    @Column(name="message")
    private String message;
    @NotBlank
    @Column(name="read_status")
    private String readStatus;
    @NotBlank
    @Column(name="time_stamp")
    private Date timeStamp;
    @Column(name="deleted_by")
    private int deleted_by;

    @ManyToOne(cascade=CascadeType.ALL,fetch = FetchType.LAZY)
    private ChatUser chatUsers;

    public Chat(String message, String status, Date timeSent) {
        this.message=message;
        this.readStatus=status;
        this.timeStamp=timeSent;
    }

    public void setChatUser(ChatUser chatUser) {
        this.chatUsers = chatUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chat chat = (Chat) o;
        return id == chat.id;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
