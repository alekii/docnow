package com.docnow.docnow.chat.Entity;

import com.docnow.docnow.chat.model.Status;

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
    @Column(name="message_id")
    private int id;

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
    private ChatUsers chatUsers;

    public Chat(String message, String status, Date timeSent,ChatUsers chatUsers) {
        this.message=message;
        this.readStatus=status;
        this.timeStamp=timeSent;
        this.chatUsers = chatUsers;
    }

}
