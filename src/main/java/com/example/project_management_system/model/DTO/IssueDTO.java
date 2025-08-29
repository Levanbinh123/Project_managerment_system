package com.example.project_management_system.model.DTO;

import com.example.project_management_system.model.Project;
import com.example.project_management_system.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueDTO {
    private Long id;
    private String title;
    private String description;
    private String status;
    private String priority;
    private LocalDate dueDate;

    private Long projectId;
    private String projectName;

    private Long assigneeId;
    private String assigneeName;

    private List<String> tags;
}
