package com.example.project_management_system.controller;


import com.example.project_management_system.model.Message;
import com.example.project_management_system.request.CreateMessageRequest;
import com.example.project_management_system.service.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class MessageSocketController {

    @Autowired
    private MessageService messageService;

    @MessageMapping("/chat.sendMessage") // Client gửi tới /app/chat.sendMessage
    @SendTo("/topic/messages") // Gửi broadcast đến tất cả subscriber
    public Message sendMessage(CreateMessageRequest request) throws Exception {
        Message savedMessage = messageService.saveMessage(
                request.getSenderId(),
                request.getProjectId(),
                request.getContent()
        );
        return savedMessage;
    }
}