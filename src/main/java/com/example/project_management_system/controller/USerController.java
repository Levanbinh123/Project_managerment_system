package com.example.project_management_system.controller;

import com.example.project_management_system.model.User;
import com.example.project_management_system.response.AuthResponse;
import com.example.project_management_system.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class USerController {
    @Autowired
    private UserService userService;
    @GetMapping("/profile")
    public ResponseEntity<User> getUserProfile(@RequestHeader("Authorization") String token) throws Exception {
        User user=userService.findUserProfileByJwt(token);
        return ResponseEntity.ok(user);
    }
    @GetMapping("/admin")
    public ResponseEntity<List<User>> getAllUsers() throws Exception {
        List<User> users=userService.findAllUser();
        return ResponseEntity.ok(users);
    }
    @PatchMapping("/admin/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable("userId") Long userId,@RequestBody User user) throws Exception {
        User user1=userService.updatedUser(userId,user);
        return ResponseEntity.ok(user1);
    }
    @DeleteMapping("/admin/{userId}")
    public ResponseEntity<AuthResponse> deleteUser(@PathVariable("userId") Long userId) throws Exception {
        userService.deletedUser(userId);
        AuthResponse authResponse=new AuthResponse();
        authResponse.setMessage("User deleted successfully");
        return ResponseEntity.ok(authResponse);
    }


}
