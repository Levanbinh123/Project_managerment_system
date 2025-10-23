package com.example.project_management_system.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import javax.xml.stream.events.Comment;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String status;
    private Long projectID;
    private String priority;
    private LocalDate dueDate;
    @ElementCollection
    private List<String> tags=new ArrayList<>();
    @ManyToOne
    @JsonIgnoreProperties({"assignedIssues", "projects"})
    private User assignee;
    @ManyToOne
    @JoinColumn(name = "project_id")
    @JsonIgnoreProperties("issues")
    private Project project;
    @JsonIgnore
    @OneToMany(mappedBy = "issue",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comments> comments = new ArrayList<>();

}
