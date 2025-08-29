package com.example.project_management_system.repository;

import com.example.project_management_system.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ChatRepository extends JpaRepository<Chat, Long> {


}
