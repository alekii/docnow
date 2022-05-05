package com.docnow.docnow.chat.controller;

import com.docnow.docnow.chat.Entity.Chat;
import com.docnow.docnow.chat.Entity.ChatUser;
import com.docnow.docnow.chat.model.Message;
import com.docnow.docnow.chat.repository.ChatRepository;
import com.docnow.docnow.chat.repository.ChatUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate template;
    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private ChatUserRepository chatUsersRepository;

    @MessageMapping("/message")
    public Message receiverMessage(@Payload Message message) {
        ChatUser chatUser = new ChatUser(message.getSender_id(),message.getReceiver_id());
        Chat chat = new Chat(message.getMessage(), message.getStatus().name(), message.getTimeSent());
        chatUser.addChat(chat);
        chatUsersRepository.save(chatUser);
        template.convertAndSendToUser(String.valueOf(message.getReceiver_id()), "/private", message);
        return message;
    }
}
