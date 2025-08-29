package com.example.project_management_system.repository;

import com.example.project_management_system.model.Project;
import com.example.project_management_system.model.ProjectDTO.UpdateProject;
import com.example.project_management_system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByOwner(User user);
    List<Project> findByNameContainingAndTeamContaining(String partialName, User user);

    List<Project> findByTeamContainingOrOwner(User user, User owner);
}
