package com.example.project_management_system.request;

import lombok.Data;

import java.awt.font.LayoutPath;
@Data
public class CreateMessageRequest {
    private Long senderId;
    private String content;
    private Long projectId;
}
