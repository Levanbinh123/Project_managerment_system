package com.example.project_management_system.service.impl;

import com.example.project_management_system.model.DTO.IssueDTO;
import com.example.project_management_system.model.Issue;
import com.example.project_management_system.model.Project;
import com.example.project_management_system.model.User;
import com.example.project_management_system.repository.IssueRepository;
import com.example.project_management_system.repository.ProjectRepository;
import com.example.project_management_system.request.IssueRequest;
import com.example.project_management_system.service.service.IssueService;
import com.example.project_management_system.service.service.ProjectService;
import com.example.project_management_system.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class IssueServiceImpl implements IssueService {
    @Autowired
    private IssueRepository issueRepository;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UserService userService;
    @Override
    public Issue getIssueNyId(Long issueId) throws Exception {
        Optional<Issue> issue = issueRepository.findById(issueId);
        Issue issue1 = issue.get();
        if(issue.isPresent()){
            return issue1;
        }
        throw new Exception("Issue not found"+issueId);
    }

    @Override
    public List<Issue> getIssuesByProjectId(Long projectId) throws Exception {
        return issueRepository.findByProjectId(projectId);
    }

    @Override
    public Issue createIssue(IssueRequest issue, User user) throws Exception {
        Project project = projectRepository.findById(issue.getProjectId()).get();
        Issue newIssue = new Issue();
        newIssue.setTitle(issue.getTitle());
        newIssue.setDescription(issue.getDescription());
        newIssue.setStatus(issue.getStatus());
        newIssue.setProjectID(issue.getProjectId());
        newIssue.setPriority(issue.getPriority());
        newIssue.setDueDate(issue.getDueDate());
        newIssue.setProject(project);
        return issueRepository.save(newIssue);
    }

    @Override
    public void deleteIssue(Long issueId, Long userId) throws Exception {
       getIssueNyId(issueId);
       issueRepository.deleteById(issueId);

    }

    @Override
    public Issue addUserToIssue(Long issueId, Long userId) throws Exception {
        User user=userService.findUserById(userId);
        Issue issue=issueRepository.findById(issueId).get();
        issue.setAssignee(user);
        return issueRepository.save(issue);
    }

    @Override
    public Issue updateStatus(Long issueId, String status) throws Exception {
        Issue issue=getIssueNyId(issueId);
        issue.setStatus(status);
        return issueRepository.save(issue);
    }
}
