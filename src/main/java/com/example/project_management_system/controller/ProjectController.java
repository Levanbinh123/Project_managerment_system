package com.example.project_management_system.controller;

import com.example.project_management_system.model.Chat;
import com.example.project_management_system.model.ProjectDTO.ProjectDTO;
import com.example.project_management_system.model.Invitation;
import com.example.project_management_system.model.Project;
import com.example.project_management_system.model.ProjectDTO.UpdateProject;
import com.example.project_management_system.model.User;
import com.example.project_management_system.repository.ProjectRepository;
import com.example.project_management_system.request.InviteRequest;
import com.example.project_management_system.response.MessageResponse;
import com.example.project_management_system.service.service.InvitationService;
import com.example.project_management_system.service.service.ProjectService;
import com.example.project_management_system.service.service.UserService;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    @Autowired
    private ProjectRepository  projectRepository;
    @Autowired
    private InvitationService invitationService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private UserService userService;
    @GetMapping
    public ResponseEntity<List<ProjectDTO>> getProjects(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String tag,
            @RequestHeader("Authorization")String token
    )throws Exception {
        User user=userService.findUserProfileByJwt(token);
        List<ProjectDTO> projects=projectService.getProjectByTeam(user,category,tag);
        return new ResponseEntity<>(projects,HttpStatus.OK);
    }
    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectDTO>getProjectId(
            @PathVariable Long projectId,
            @RequestHeader("Authorization")String token
    )throws Exception {
        User user=userService.findUserProfileByJwt(token);
        ProjectDTO project=projectService.getProjectById(projectId);
        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<MessageResponse>createProject(
            @RequestHeader("Authorization")String token,
            @RequestBody ProjectDTO project
    )throws Exception {
        User user=userService.findUserProfileByJwt(token);
        projectService.createProject(project,user);
        MessageResponse messageResponse=new MessageResponse("Project created successfully");
        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }
    @PatchMapping("/{projectId}")
    public ResponseEntity<MessageResponse>updateProject(
            @PathVariable Long projectId,
            @RequestBody UpdateProject project
    )throws Exception {
        Project updatedProject=projectService.updateProject(projectId,project);
        MessageResponse mes= new MessageResponse("Project updated successfully");
        return new ResponseEntity<>(mes, HttpStatus.OK);
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<MessageResponse>deleteProject(
            @PathVariable Long projectId,
            @RequestHeader("Authorization")String token
    )throws Exception {
        User user=userService.findUserProfileByJwt(token);
        projectService.deleteProject(projectId,user.getId());
        MessageResponse res=new MessageResponse();
        res.setMessage("success");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
    @GetMapping("/search")
    public ResponseEntity<List<ProjectDTO>> searchProjects(
            @RequestParam(required = false) String  keyword,
            @RequestHeader("Authorization")String token
    )throws Exception {
        User user=userService.findUserProfileByJwt(token);
        List<ProjectDTO> projects=projectService.searchProjects(keyword,user);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/{projectId}/chat")
    public ResponseEntity<Chat>getChatByProjectId(
            @PathVariable Long projectId
    )throws Exception {
        Chat chat=projectService.getChatByProjectId(projectId);
        return new ResponseEntity<>(chat, HttpStatus.OK);
    }
    @PostMapping("/invite")
    public ResponseEntity<MessageResponse>inviteProject(
            @RequestBody InviteRequest req,
            @RequestHeader("Authorization") String jwt
            ) throws Exception {
        invitationService.sendInviation(req.getEmail(),req.getProjectId());
        MessageResponse res=new MessageResponse("user inviated");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/accept_invitation")
    public ResponseEntity<Invitation>acceptInviteProject(
            @RequestParam String token,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user=userService.findUserProfileByJwt(jwt);
        Invitation invitation=invitationService.accepInvitation(token,user.getId());
        projectService.addUserToProject(invitation.getProjectId(),user.getId());
        return new ResponseEntity<>(invitation, HttpStatus.ACCEPTED);
    }

}
