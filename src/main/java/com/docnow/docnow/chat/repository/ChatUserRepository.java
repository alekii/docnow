package com.docnow.docnow.chat.repository;

import com.docnow.docnow.chat.Entity.ChatUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ChatUserRepository extends JpaRepository<ChatUser,Long> {
   //Create JPQL to find chat
    Optional<ChatUser> findById(int ChatId);

    //JOIN FETCH to the rescue: solves LazyInitializationException
    @Query("SELECT u FROM ChatUser u JOIN FETCH u.chats d where u.id =:id ")
    ChatUser findChatUser(@Param("id") long id);
}











