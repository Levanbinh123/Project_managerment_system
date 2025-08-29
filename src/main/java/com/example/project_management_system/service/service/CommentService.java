package com.example.project_management_system.service.service;

import com.example.project_management_system.model.Comments;

import javax.xml.stream.events.Comment;
import java.util.List;

public interface CommentService {
    Comments createComment(Long issueId, Long userId, String content) throws Exception;
    void deleteComment(Long commentId, Long userId) throws Exception;
    List<Comments> findCommentsByIssueId(Long issueId);

}
