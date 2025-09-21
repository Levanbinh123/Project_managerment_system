package com.example.project_management_system.request;

import com.example.project_management_system.model.Chat;
import com.example.project_management_system.model.User;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SendMessageResponse {
    private Long id;
    private String content;
    private LocalDateTime createdAt;
}
