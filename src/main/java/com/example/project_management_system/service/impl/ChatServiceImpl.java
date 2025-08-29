package com.example.project_management_system.service.impl;

import com.example.project_management_system.model.Chat;
import com.example.project_management_system.repository.ChatRepository;
import com.example.project_management_system.service.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements ChatService {
    @Autowired
    private ChatRepository chatRepository;

    @Override
    public Chat createChat(Chat chat) {
        return chatRepository.save(chat);
    }
}
