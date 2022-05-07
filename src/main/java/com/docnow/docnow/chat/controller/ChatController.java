package com.docnow.docnow.chat.controller;

import com.docnow.docnow.chat.Entity.Chat;
import com.docnow.docnow.chat.Entity.ChatUser;
import com.docnow.docnow.chat.model.Message;
import com.docnow.docnow.chat.repository.ChatRepository;
import com.docnow.docnow.chat.repository.ChatUserRepository;
import com.docnow.docnow.chat.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate template;
    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private ChatService chatService;
    @Autowired
    private ChatUserRepository chatUsersRepository;
    int[] primeNumbers= {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101};
    String[] alphabetLetters = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
    List<String> alphabetLettersList = new ArrayList<>();

    @MessageMapping("/message")
    public Message receiverMessage(@Payload Message message) {
        saveMessage(message);
        template.convertAndSendToUser(String.valueOf(message.getReceiver_id()), "/private", message);
        return message;
    }

    private void saveMessage(Message message) {
        long chatId = generateChatId();
        Chat chat = new Chat(message.getMessage());
        Optional<ChatUser> chatUserOptional = chatUsersRepository.findById(chatId);
        ChatUser chatUser;
        if(chatUserOptional.isEmpty()){
            chatUser = new ChatUser(chatId, "user1", "user2");
        }
        else {
            chatUser =chatService.findChatUser(chatId);
        }
        chatUser.addChat(chat);
        chatUsersRepository.save(chatUser);
    }

    private long generateChatId() {
        alphabetLettersList.addAll(Arrays.asList(alphabetLetters).subList(0, alphabetLetters.length - 1));
        String combinedName = "user1".concat("user2");
        long result = 1;
        //ChatId generation Algorithm
        for(int i = 0; i<combinedName.length();i++){
            double a = primeNumbers[i];
            double b =alphabetLettersList.indexOf(alphabetLetters[i]);
            double primeRaised = Math.pow(a,b);
            result*=result*primeRaised;
        }
        return result;
    }

    //Unique(Find the chatsids where sender id or receiver id )= username in chat user table
    //we will get the username from authentication object and not from client
    //for every chastid
    //get the chats in chats table where id = chatsid
    //first load last 100 messages into memory for every chat
    //we should store this data in cache but will do this later
    //where sender id= username put chat in right hand side alongside timestamp
    //where receiver id = username put chat in left hand side plus timestamp
}
