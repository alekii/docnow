package com.docnow.docnow.chat.services;

import com.docnow.docnow.chat.Entity.ChatUser;
import com.docnow.docnow.chat.repository.ChatUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
    @Autowired
    ChatUserRepository chatUserRepository;
    public ChatUser findChatUser(long id){
        return chatUserRepository.findChatUser(id);
    }
}
