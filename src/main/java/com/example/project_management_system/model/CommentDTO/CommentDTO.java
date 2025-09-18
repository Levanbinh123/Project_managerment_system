package com.example.project_management_system.model.CommentDTO;

import com.example.project_management_system.model.User;
import lombok.Data;

@Data
public class CommentDTO {
    private Long id;
    private String content;
    private User user;
}
