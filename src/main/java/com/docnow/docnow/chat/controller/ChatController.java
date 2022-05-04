package com.docnow.docnow.chat.controller;

import com.docnow.docnow.chat.Entity.Chat;
import com.docnow.docnow.chat.Entity.ChatUsers;
import com.docnow.docnow.chat.model.Message;
import com.docnow.docnow.chat.repository.ChatRepository;
import com.docnow.docnow.chat.repository.ChatUsersRepository;
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
    private ChatUsersRepository chatUsersRepository;

    @MessageMapping("/message")
    public Message receiverMessage(@Payload Message message) {
        ChatUsers chatUser = new ChatUsers(message.getSender_id(),message.getReceiver_id());
        Chat chat = new Chat(message.getMessage(), message.getStatus().name(), message.getTimeSent(),chatUser);
        chatUsersRepository.save(chatUser);
        chatRepository.save(chat);
        template.convertAndSendToUser(String.valueOf(message.getReceiver_id()), "/private", message);
        return message;
    }
}
