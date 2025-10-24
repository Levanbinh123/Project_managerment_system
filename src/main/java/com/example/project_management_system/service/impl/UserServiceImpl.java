package com.example.project_management_system.service.impl;

import com.example.project_management_system.config.JwtProvider;
import com.example.project_management_system.model.Chat;
import com.example.project_management_system.model.Issue;
import com.example.project_management_system.model.Project;
import com.example.project_management_system.model.User;
import com.example.project_management_system.repository.*;
import com.example.project_management_system.service.service.UserService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired
    private IssueRepository issueRepository;
    @Autowired
    private EntityManager entityManager;
    @Override
    public User findUserProfileByJwt(String jwt) throws Exception {

        String email= JwtProvider.getEmailFormToken(jwt);
        return findUserByEmail(email);
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new Exception("user not found");
        }

        return user;
    }

    @Override
    public User findUserById(Long userId) throws Exception {
        Optional<User> user = userRepository.findById(userId);
        return user.orElse(null);
    }

    @Override
    public User updateUsersProjectSize(User user, int number) throws Exception {
        user.setProjectSize(user.getProjectSize()+number);
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void deletedUser(Long userId) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        entityManager.createNativeQuery("DELETE FROM chat_users WHERE users_id = :userId")
                .setParameter("userId", userId)
                .executeUpdate();
        // 1. Xóa user khỏi các team của project
        List<Project> projects = projectRepository.findProjectsByTeamContaining(user);
        for (Project project : projects) {
            project.getTeam().remove(user);
            projectRepository.save(project); // Lưu thay đổi
        }

        // 2. Xử lý projects mà user là owner (set null hoặc xóa)
        List<Project> ownedProjects = projectRepository.findByOwner(user);
        for (Project project : ownedProjects) {
            project.setOwner(null); // hoặc xóa project nếu cần
            projectRepository.save(project);
        }
        // 4. Xử lý issues mà user là assignee (set null)
        List<Issue> assignedIssues = issueRepository.findByAssignee(user);
        for (Issue issue : assignedIssues) {
            issue.setAssignee(null);
            issueRepository.save(issue);
        }

        // 3. Xóa subscription
        subscriptionRepository.deleteByUserId(userId);

        // 4. Xóa user
        userRepository.delete(user);
    }

    @Override
    public User updatedUser(Long userId, User user) throws Exception {
        User updatedUser = userRepository.findById(userId).orElse(null);
        if (user.getRole() != null) {
            updatedUser.setRole(user.getRole());
        }
        if (user.getFullName() != null) {
            updatedUser.setFullName(user.getFullName());
        }
        if (user.getEmail() != null) {
            updatedUser.setEmail(user.getEmail());
        }
        User userUpdated = userRepository.save(updatedUser);
        return userUpdated;
    }

    @Override
    public List<User> findAllUser() throws Exception {
        return userRepository.findAll();
    }
}
