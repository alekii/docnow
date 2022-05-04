package com.docnow.docnow.chat.repository;

import com.docnow.docnow.chat.Entity.ChatUsers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatUsersRepository extends JpaRepository<ChatUsers,Integer> {

}
