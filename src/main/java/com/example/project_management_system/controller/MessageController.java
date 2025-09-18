package com.example.project_management_system.controller;

import com.example.project_management_system.config.JwtProvider;
import com.example.project_management_system.model.Chat;
import com.example.project_management_system.model.Message;
import com.example.project_management_system.model.User;
import com.example.project_management_system.request.CreateMessageRequest;
import com.example.project_management_system.service.service.MessageService;
import com.example.project_management_system.service.service.ProjectService;
import com.example.project_management_system.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/message")
public class MessageController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private UserService userService;
    @PostMapping("/send")
    public ResponseEntity<Message> sendMessages(@RequestBody CreateMessageRequest createMessageRequest) throws Exception {
        User user=userService.findUserById(createMessageRequest.getSenderId());
        if(user==null){
            throw new Exception("user not found");
        }
        Chat chats=projectService.getProjectById(createMessageRequest.getProjectId()).getChat();
        if(chats==null){
            throw new Exception("chat not found");
        }
        Message sendMessage=messageService.saveMessage(user.getId(), createMessageRequest.getProjectId(),createMessageRequest.getContent());

        return ResponseEntity.ok(sendMessage);
    }
    @GetMapping("/chat/{projectId}")
    public ResponseEntity<List<Message>> getMessagesByProjectId(@PathVariable Long projectId) throws Exception {
        List<Message> messages=messageService.getMessagesByProjectId(projectId);
        return ResponseEntity.ok(messages);
    }



}
