package com.example.project_management_system.service.service;

import com.example.project_management_system.model.Chat;
import com.example.project_management_system.model.ProjectDTO.ProjectDTO;
import com.example.project_management_system.model.Project;
import com.example.project_management_system.model.ProjectDTO.UpdateProject;
import com.example.project_management_system.model.User;

import java.util.List;

public interface ProjectService {
    Project createProject(ProjectDTO project, User user) throws Exception;
    List<ProjectDTO> getProjectByTeam(User user, String category, String tag) throws Exception;
    ProjectDTO getProjectById(Long id) throws Exception;
    void deleteProject(Long projectId, Long userId) throws Exception;
    Project updateProject(Long id, UpdateProject project) throws Exception;
    void addUserToProject(Long projectId, Long userId) throws Exception;
    void removeUserFromProject(Long projectId, Long userId) throws Exception;

    Chat getChatByProjectId(Long projectId) throws Exception;
    List<ProjectDTO> searchProjects(String keyWord, User user) throws Exception;
}
