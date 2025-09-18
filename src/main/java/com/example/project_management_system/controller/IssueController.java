package com.example.project_management_system.controller;

import com.example.project_management_system.model.DTO.IssueDTO;
import com.example.project_management_system.model.Issue;
import com.example.project_management_system.model.User;
import com.example.project_management_system.request.IssueRequest;
import com.example.project_management_system.response.AuthResponse;
import com.example.project_management_system.response.MessageResponse;
import com.example.project_management_system.service.service.IssueService;
import com.example.project_management_system.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/issues")
public class IssueController {
    @Autowired
    private IssueService issueService;
    @Autowired
    private UserService userService;
    @GetMapping("/{issueId}")
    public ResponseEntity<IssueDTO> getIssueById(@PathVariable Long issueId) throws Exception {
            Issue issue=issueService.getIssueNyId(issueId);
        IssueDTO dto = new IssueDTO();
        dto.setId(issue.getId());
        dto.setDescription(issue.getDescription());
        dto.setDueDate(issue.getDueDate());
        dto.setPriority(issue.getPriority());
        dto.setProjectId(issue.getProject().getId());
        dto.setProjectName(issue.getProject().getName());
        dto.setAssigneeId(issue.getAssignee().getId());
        dto.setAssigneeName(issue.getAssignee().getFullName());
        dto.setStatus(issue.getStatus());
        dto.setTitle(issue.getTitle());
        dto.setTags(issue.getTags());
        return ResponseEntity.ok(dto);


    }
    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<IssueDTO>> getIssuesByProjectId(@PathVariable Long projectId) throws Exception {
        List<Issue> issue=issueService.getIssuesByProjectId(projectId);
        List<IssueDTO> dtos=new ArrayList<>();
        for(Issue i:issue){
            IssueDTO dto=new IssueDTO();
            dto.setId(i.getId());
            dto.setDescription(i.getDescription());
            dto.setDueDate(i.getDueDate());
            dto.setPriority(i.getPriority());
            dto.setProjectId(i.getProject().getId());
            dto.setProjectName(i.getProject().getName());
            dto.setStatus(i.getStatus());
            dto.setTitle(i.getTitle());
            dtos.add(dto);
        }
        return ResponseEntity.ok(dtos);

    }
    @PostMapping
public ResponseEntity<IssueDTO> createIssue(@RequestBody IssueRequest issueRequest, @RequestHeader("Authorization") String token) throws Exception {
        User tokenUser=userService.findUserProfileByJwt(token);
        User user=userService.findUserById(tokenUser.getId());
        Issue issue=issueService.createIssue(issueRequest, user);
        IssueDTO dto = new IssueDTO();
        dto.setId(issue.getId());
        dto.setTitle(issue.getTitle());
        dto.setDescription(issue.getDescription());
        dto.setStatus(issue.getStatus());
        dto.setPriority(issue.getPriority());
        dto.setDueDate(issue.getDueDate());
        dto.setProjectId(issue.getProject().getId());
        dto.setProjectName(issue.getProject().getName());
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
    @DeleteMapping("/{issueId}")
    public ResponseEntity<MessageResponse> deleteIssue(@PathVariable Long issueId, @RequestHeader("Authorization") String token) throws Exception {
        User user=userService.findUserProfileByJwt(token);
        issueService.deleteIssue(issueId,user.getId());
        MessageResponse authResponse=new MessageResponse();
        authResponse.setMessage("Successfully deleted");
        return ResponseEntity.ok(authResponse);
    }
    @PutMapping("/{issueId}/assignee/{userId}")
    public ResponseEntity<MessageResponse> addUserToIssue( @PathVariable Long issueId, @PathVariable Long userId) throws Exception {
        Issue issue=issueService.addUserToIssue(issueId, userId);
        MessageResponse mes=new MessageResponse("Successfully added");
        return ResponseEntity.ok(mes);
    }
    @PutMapping("/{issueId}/status/{status}")
    public ResponseEntity<MessageResponse> updateIssueStatus(@PathVariable Long issueId, @PathVariable String status) throws Exception {
        issueService.updateStatus(issueId, status);
        MessageResponse mes=new MessageResponse("Successfully updated");
        return ResponseEntity.ok(mes);
    }
}
