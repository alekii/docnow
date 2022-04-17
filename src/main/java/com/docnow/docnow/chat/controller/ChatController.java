package com.docnow.docnow.chat.controller;

import com.docnow.docnow.chat.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate template;

   @MessageMapping("/message")
    public Message receiverMessage(@Payload Message message){
      template.convertAndSendToUser(message.getReceiverName() ,"/private",message);
      return message;
   }

}
