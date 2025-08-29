package com.example.project_management_system.controller;

import com.example.project_management_system.model.Subcripstion;
import com.example.project_management_system.model.User;
import com.example.project_management_system.model.enum_.PlanType;
import com.example.project_management_system.service.service.SubcriptionService;
import com.example.project_management_system.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/supscription")
public class SubcriptionController {
    @Autowired
    private SubcriptionService subcriptionService;
    @Autowired
    private UserService userService;
    @GetMapping
    public ResponseEntity<Subcripstion> getAllSubscriptions(@RequestHeader("Authorization") String token) throws Exception {
        User user=userService.findUserProfileByJwt(token);
        Subcripstion subcripstion=subcriptionService.getUserSubcription(user.getId());
        return new ResponseEntity<>(subcripstion, HttpStatus.OK);

    }
    @PatchMapping("/upgrade")
    public ResponseEntity<Subcripstion> upgradeSubscription(@RequestHeader("Authorization") String token,
                                                            @RequestParam PlanType planType) throws   Exception{
        User user=userService.findUserProfileByJwt(token);
        Subcripstion subcripstion=subcriptionService.upgradeSubcription(user.getId(), planType);
        return new ResponseEntity<>(subcripstion, HttpStatus.OK);
    }
}
