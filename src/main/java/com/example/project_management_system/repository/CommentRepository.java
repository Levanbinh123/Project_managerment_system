package com.example.project_management_system.repository;

import com.example.project_management_system.model.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.xml.stream.events.Comment;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comments, Long> {
    List<Comments> findByIssueId(Long issueId);
}
