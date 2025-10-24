package com.example.project_management_system.repository;

import com.example.project_management_system.model.Issue;
import com.example.project_management_system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IssueRepository extends JpaRepository<Issue, Long> {
public List<Issue> findByProjectId(Long projectId);
    List<Issue> findByAssignee(User assignee);
}
