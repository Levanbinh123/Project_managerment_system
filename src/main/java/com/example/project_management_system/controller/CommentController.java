package com.example.project_management_system.controller;

import com.example.project_management_system.model.CommentDTO.CommentDTO;
import com.example.project_management_system.model.Comments;
import com.example.project_management_system.model.User;
import com.example.project_management_system.request.CreateCommentRequest;
import com.example.project_management_system.response.MessageResponse;
import com.example.project_management_system.service.service.CommentService;
import com.example.project_management_system.service.service.UserService;
import jakarta.persistence.PostRemove;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;
    @PostMapping
    public ResponseEntity<CommentDTO> createComment(@RequestBody CreateCommentRequest createCommentRequest,
                                                    @RequestHeader("Authorization") String token
                                                  ) throws Exception {
        User user=userService.findUserProfileByJwt(token);
       Comments comment= commentService.createComment(createCommentRequest.getIssueId(),user.getId(),createCommentRequest.getContent());
       CommentDTO dto=new CommentDTO();
       dto.setId(comment.getId());
       dto.setContent(comment.getContent());
       dto.setUser(user);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
    @DeleteMapping("/{commentId}")
    public ResponseEntity<MessageResponse> deleteComment(@PathVariable Long commentId, @RequestHeader("Authorization") String token) throws Exception {
        User user=userService.findUserProfileByJwt(token);
        commentService.deleteComment(commentId,user.getId());
        MessageResponse messageResponse=new MessageResponse();
        messageResponse.setMessage("Comment deleted");
        return new ResponseEntity<>(messageResponse,HttpStatus.OK);
    }
    @GetMapping("/{issueId}")
    public ResponseEntity<List<CommentDTO>> getCommentsByIssueId(@PathVariable Long issueId) {
        List<Comments> comments=commentService.findCommentsByIssueId(issueId);

    List<CommentDTO> dtos=new ArrayList<>();
        for(Comments comment:comments){
            CommentDTO dto=new CommentDTO();
            dto.setId(comment.getId());
            dto.setContent(comment.getContent());
            dto.setUser(comment.getUser());
            dtos.add(dto);
        }
        return new ResponseEntity<>(dtos,HttpStatus.OK);
    }


}
