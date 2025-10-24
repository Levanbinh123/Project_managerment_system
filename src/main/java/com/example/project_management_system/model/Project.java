package com.example.project_management_system.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String category;
    @ElementCollection
    private List<String> tags= new ArrayList<>();
    @JsonIgnore
    @OneToOne(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private Chat chat;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JsonIgnoreProperties({"assignedIssues", "projects"})
    private User owner;
    @OneToMany(mappedBy = "project",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Issue> issues = new ArrayList<>();
    @ManyToMany( fetch = FetchType.EAGER)
    @JoinTable(
            name = "project_team", // tên bảng trung gian
            joinColumns = @JoinColumn(name = "project_id"), // khóa ngoại của Project
            inverseJoinColumns = @JoinColumn(name = "user_id") // khóa ngoại của User
    )
    private List<User>team= new ArrayList<>();
}
