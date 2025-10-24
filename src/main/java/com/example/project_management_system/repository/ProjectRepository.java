package com.example.project_management_system.repository;

import com.example.project_management_system.model.Project;
import com.example.project_management_system.model.ProjectDTO.UpdateProject;
import com.example.project_management_system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByOwner(User user);
    List<Project> findByNameContainingAndTeamContaining(String partialName, User user);
    @Query("SELECT p FROM Project p LEFT JOIN FETCH p.chat WHERE p.id = :id")
    Optional<Project> findByIdWithChat(@Param("id") Long id);
    List<Project> findByTeamContainingOrOwner(User user, User owner);
    @Modifying
    @Transactional
    @Query("UPDATE Project p SET p.owner = NULL WHERE p.owner.id = :userId")
    void unassignProjects(@Param("userId") Long userId);

    @Query("SELECT p FROM Project p WHERE :user MEMBER OF p.team")
    List<Project> findProjectsByTeamContaining(@Param("user") User user);
}
