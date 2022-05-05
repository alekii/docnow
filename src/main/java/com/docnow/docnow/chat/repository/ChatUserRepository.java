package com.docnow.docnow.chat.repository;

import com.docnow.docnow.chat.Entity.ChatUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatUserRepository extends JpaRepository<ChatUser,Integer> {

}
