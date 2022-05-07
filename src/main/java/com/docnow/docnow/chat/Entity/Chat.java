package com.docnow.docnow.chat.Entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;


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
    @Column(name="read_status")
    private String readStatus;
    @Column(name="time_stamp")
    private Date timeStamp;
    @Column(name="deleted_by")
    private int deleted_by;

    @ManyToOne(cascade=CascadeType.ALL,  fetch = FetchType.LAZY )
    private ChatUser chatUsers;

    public Chat(String message, String status, Date timeSent) {
        this.message=message;
        this.readStatus=status;
        this.timeStamp=timeSent;
    }

    public Chat(String message) {
        this.message=message;
    }

    public Chat() {
    }

    public Chat(long id, String message, String readStatus, Date timeStamp, int deleted_by, ChatUser chatUsers) {
        this.id = id;
        this.message = message;
        this.readStatus = readStatus;
        this.timeStamp = timeStamp;
        this.deleted_by = deleted_by;
        this.chatUsers = chatUsers;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(String readStatus) {
        this.readStatus = readStatus;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getDeleted_by() {
        return deleted_by;
    }

    public void setDeleted_by(int deleted_by) {
        this.deleted_by = deleted_by;
    }

    public ChatUser getChatUsers() {
        return chatUsers;
    }

    public void setChatUsers(ChatUser chatUsers) {
        this.chatUsers = chatUsers;
    }
}
