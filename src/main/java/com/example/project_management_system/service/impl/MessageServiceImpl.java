package com.example.project_management_system.service.impl;

import com.example.project_management_system.model.Chat;
import com.example.project_management_system.model.Message;
import com.example.project_management_system.model.User;
import com.example.project_management_system.repository.ChatRepository;
import com.example.project_management_system.repository.MessageRepository;
import com.example.project_management_system.repository.UserRepository;
import com.example.project_management_system.service.service.MessageService;
import com.example.project_management_system.service.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private ChatRepository  chatRepository;

    @Override
    public Message saveMessage( Long senderId,Long projectId, String content) throws Exception {
       User user=userRepository.findById(senderId).get();
        Chat chat=projectService.getProjectById(projectId).getChat();
        Message message=new Message();
        message.setSender(user);
        message.setContent(content);
        message.setCreatedAt(LocalDateTime.now());
        message.setChat(chat);
        Message savedMessage=messageRepository.save(message);
        chat.getMessages().add(savedMessage);
        return savedMessage;
    }

    @Override
    public List<Message> getMessagesByProjectId(Long projectId) throws Exception {
        Chat chat=projectService.getChatByProjectId(projectId);
        List<Message> findByChatIdOrderByCreatedAtAsc=messageRepository.findByChatIdOrderByCreatedAtAsc(chat.getId());
        return findByChatIdOrderByCreatedAtAsc;

    }
}
