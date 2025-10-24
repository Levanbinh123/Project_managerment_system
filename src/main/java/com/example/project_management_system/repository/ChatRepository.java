package com.example.project_management_system.repository;

import com.example.project_management_system.model.Chat;
import com.example.project_management_system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    @Query("SELECT c FROM Chat c WHERE :user MEMBER OF c.users")
    List<Chat> findChatsByUsersContaining(@Param("user") User user);

}
