package com.example.project_management_system.model.ProjectDTO;

import com.example.project_management_system.model.Chat;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class ProjectDTO {
    private Long id;
    private String name;
    private String description;
    private String category;
    private List<String> tags= new ArrayList<>();
    private Chat chat;
}
