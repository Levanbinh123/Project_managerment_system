package com.example.project_management_system.service.service;

import com.example.project_management_system.model.Message;

import java.util.List;

public interface MessageService {
    Message saveMessage(Long senderId, Long projectId, String content) throws Exception;
    List<Message> getMessagesByProjectId(Long projectId) throws Exception;


}
