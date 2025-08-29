package com.example.project_management_system.model.ProjectDTO;

import com.example.project_management_system.model.Chat;
import com.example.project_management_system.model.User;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class CreateProject {
    private String name;
    private String description;
    private String category;
    private List<String> tags= new ArrayList<>();
}
