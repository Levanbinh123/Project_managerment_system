package com.example.project_management_system.service.impl;

import com.example.project_management_system.model.Comments;
import com.example.project_management_system.model.Issue;
import com.example.project_management_system.model.User;
import com.example.project_management_system.repository.CommentRepository;
import com.example.project_management_system.repository.IssueRepository;
import com.example.project_management_system.repository.UserRepository;
import com.example.project_management_system.service.service.CommentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private IssueRepository issueRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    public Comments createComment(Long issueId, Long userId, String content) throws Exception {
        Optional<Issue> issue = issueRepository.findById(issueId);
        Optional<User> user = userRepository.findById(userId);
        Issue issue1 = issue.get();
        User user1 = user.get();
        Comments comments = new Comments();
        comments.setIssue(issue1);
        comments.setUser(user1);
        comments.setCreatedDateTime(LocalDateTime.now());
        comments.setContent(content);
        issue1.getComments().add(comments);
        Comments savedComment=commentRepository.save(comments);
        issue1.getComments().add(savedComment);
        return savedComment;

    }

    @Override
    public void deleteComment(Long commentId, Long userId) throws Exception {
        Optional<Comments> comments = commentRepository.findById(commentId);
        Optional<User> user = userRepository.findById(userId);
        if(comments.isEmpty()){
            throw new Exception("Comment not found");
        }
        if(user.isEmpty()){
            throw new Exception("user not found");
        }
        Comments comments1 = comments.get();
        User user1 = user.get();
        if(comments1.getUser().equals(user1)){
            commentRepository.deleteById(commentId);
        }else {
            throw new Exception("Comment not deleted");
        }
    }

    @Override
    public List<Comments> findCommentsByIssueId(Long issueId) {
        return commentRepository.findByIssueId(issueId);
    }
}
