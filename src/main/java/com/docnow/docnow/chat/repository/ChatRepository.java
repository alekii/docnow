package com.docnow.docnow.chat.repository;

import com.docnow.docnow.chat.Entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Integer> {

}
