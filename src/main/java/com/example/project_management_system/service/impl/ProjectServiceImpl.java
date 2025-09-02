package com.example.project_management_system.service.impl;

import com.example.project_management_system.model.Chat;
import com.example.project_management_system.model.ProjectDTO.ProjectDTO;
import com.example.project_management_system.model.Project;
import com.example.project_management_system.model.ProjectDTO.UpdateProject;
import com.example.project_management_system.model.User;
import com.example.project_management_system.repository.ProjectRepository;
import com.example.project_management_system.service.service.ChatService;
import com.example.project_management_system.service.service.ProjectService;
import com.example.project_management_system.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ChatService chatService;

    @Override
    public Project createProject(ProjectDTO project, User user) throws Exception {
        Project createdProject = new Project();
        createdProject.setName(project.getName());
        createdProject.setTags(project.getTags());
        createdProject.setDescription(project.getDescription());
        createdProject.setOwner(user);
        createdProject.setCategory(project.getCategory());
        createdProject.getTeam().add(user);

        Chat chat = new Chat();
        chat.setName("Chat for project: " + project.getName());
        chat.setProject(createdProject);
        createdProject.setChat(chat);
        Project savedProject = projectRepository.save(createdProject);
        /// /
        return savedProject;
    }

    @Override
    public List<ProjectDTO> getProjectByTeam(User user, String category, String tag) throws Exception {
        List<Project> projects=projectRepository.findByTeamContainingOrOwner(user,user);
        if(category!=null){
            projects=projects.stream().filter(p->p.getCategory().equals(category)).collect(Collectors.toList());
        }
        if(tag!=null){
            projects=projects.stream().filter(p->p.getTags().contains(tag)).collect(Collectors.toList());
        }
        ProjectDTO dto=new ProjectDTO();
        List<ProjectDTO> dtos=new ArrayList<>();
        for(Project p: projects){
            dto.setName(p.getName());
            dto.setDescription(p.getDescription());
            dto.setTags(p.getTags());
            dto.setCategory(p.getCategory());
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public ProjectDTO getProjectById(Long id) throws Exception {
        Optional<Project> project=projectRepository.findByIdWithChat(id);
        if(project.isEmpty()){
            throw new Exception("Project not found");
        }
        Project p=project.get();
        ProjectDTO dto=new ProjectDTO();
        dto.setChat(p.getChat());
        dto.setId(p.getId());
        dto.setName(p.getName());
        dto.setDescription(p.getDescription());
        dto.setTags(p.getTags());
        dto.setCategory(p.getCategory());
        return dto;


    }

    @Override
    public void deleteProject(Long projectId, Long userId) throws Exception {
        getProjectById(projectId);
        projectRepository.deleteById(projectId);
    }

    @Override
    public Project updateProject(Long id, UpdateProject project) throws Exception {
        Project updatedProject = projectRepository.findById(id).get();
        updatedProject.setName(project.getName());
        updatedProject.setDescription(project.getDescription());
        updatedProject.setTags(project.getTags());
        Project savedPj= projectRepository.save(updatedProject);
        return savedPj;
    }

    @Override
    public void addUserToProject(Long projectId, Long userId) throws Exception {
        Project project = projectRepository.findById(projectId).get();
        User user=userService.findUserById(userId);
        if(!project.getTeam().contains(user)){
            project.getChat().getUsers().add(user);
            project.getTeam().add(user);
        }
        projectRepository.save(project);
    }

    @Override
    public void removeUserFromProject(Long projectId, Long userId) throws Exception {
        Project project = projectRepository.findById(projectId).get();
        User user=userService.findUserById(userId);
        if(!project.getTeam().contains(user)){
            project.getChat().getUsers().remove(user);
            project.getTeam().remove(user);
        }
        projectRepository.save(project);
    }

    @Override
    public Chat getChatByProjectId(Long projectId) throws Exception {
        Project project = projectRepository.findById(projectId).get();
        return project.getChat();
    }

    @Override
    public List<ProjectDTO> searchProjects(String keyWord, User user) throws Exception {
        List<Project> project= projectRepository.findByNameContainingAndTeamContaining(keyWord,user);
        List<ProjectDTO> dtos=new ArrayList<>();
        ProjectDTO dto=new ProjectDTO();
        for(Project p: project){
            dto.setName(p.getName());
            dto.setDescription(p.getDescription());
            dto.setTags(p.getTags());
            dto.setCategory(p.getCategory());
            dtos.add(dto);
        }
        return dtos;
    }
}
