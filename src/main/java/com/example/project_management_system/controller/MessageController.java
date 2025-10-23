package com.example.project_management_system.controller;


import com.example.project_management_system.model.Chat;
import com.example.project_management_system.model.Message;
import com.example.project_management_system.model.User;
import com.example.project_management_system.request.CreateMessageRequest;
import com.example.project_management_system.request.SendMessageResponse;
import com.example.project_management_system.service.service.MessageService;
import com.example.project_management_system.service.service.ProjectService;
import com.example.project_management_system.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @PostMapping("/send")
    public ResponseEntity<SendMessageResponse> sendMessages(@RequestBody CreateMessageRequest req) throws Exception {
        User user = userService.findUserById(req.getSenderId());
        Chat chat = projectService.getProjectById(req.getProjectId()).getChat();

        if (user == null || chat == null) {
            throw new Exception("User hoặc chat không tồn tại");
        }

        Message sendMessage = messageService.saveMessage(user.getId(), req.getProjectId(), req.getContent());
        SendMessageResponse res = new SendMessageResponse(sendMessage.getId(),
                sendMessage.getContent(),
                sendMessage.getCreatedAt());

        return ResponseEntity.ok(res);
    }

    @GetMapping("/chat/{projectId}")
    public ResponseEntity<List<Message>> getMessagesByProjectId(@PathVariable Long projectId) throws Exception {
        List<Message> messages = messageService.getMessagesByProjectId(projectId);
        return ResponseEntity.ok(messages);
    }

    @MessageMapping("/chat/{projectId}")
    @SendTo("/topic/project/{projectId}")
    public Message sendMessageRealtime(Message message) throws Exception {
        Long senderId = message.getSender().getId();
        Long projectId = message.getChat().getProject().getId();
        String content = message.getContent();

        Message savedMessage = messageService.saveMessage(senderId, projectId, content);

        return savedMessage;
    }
}