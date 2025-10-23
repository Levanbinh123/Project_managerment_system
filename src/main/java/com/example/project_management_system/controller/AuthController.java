package com.example.project_management_system.controller;

import com.example.project_management_system.config.JwtProvider;
import com.example.project_management_system.model.User;
import com.example.project_management_system.model.enum_.Role;
import com.example.project_management_system.repository.UserRepository;
import com.example.project_management_system.request.LoginRequest;
import com.example.project_management_system.response.AuthResponse;
import com.example.project_management_system.service.impl.CustomerUserDetaisImpl;
import com.example.project_management_system.service.service.SubcriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private SubcriptionService subcriptionService;
    @Autowired
    private CustomerUserDetaisImpl customerUserDetais;
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws Exception {
        User isUserExist=userRepository.findByEmail(user.getEmail());
        if(isUserExist!=null){
            throw new Exception("email already exists with another account");
        }
        User createdUser=new User();
        createdUser.setPassword(passwordEncoder.encode(user.getPassword()));
        createdUser.setEmail(user.getEmail());
        createdUser.setFullName(user.getFullName());
        createdUser.setRole(Role.ROLE_USER);
        User savedUser=userRepository.save(createdUser);
        subcriptionService.createSubscription(savedUser);
        Authentication authentication= new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt= JwtProvider.generateToken(authentication);
        AuthResponse res=new AuthResponse();
        res.setMessage("created success");
        res.setJwt(jwt);
        return new ResponseEntity<>(res, HttpStatus.CREATED);

    }
    @PostMapping("/singup")
    public ResponseEntity<AuthResponse> signHandler(@RequestBody LoginRequest loginRequest) throws Exception {
        String userName=loginRequest.getEmail();
        String password=loginRequest.getPassword();
        Authentication authentication=new UsernamePasswordAuthenticationToken(userName, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt= JwtProvider.generateToken(authentication);
        AuthResponse res=new AuthResponse();
        res.setMessage("singup success");
        res.setJwt(jwt);
        return new ResponseEntity<>(res, HttpStatus.CREATED);

    }
    private Authentication authenticate(String username, String password) throws Exception {
        UserDetails userDetails = customerUserDetais.loadUserByUsername(username);
        if(userDetails==null){
            throw new Exception("username not found");
        }
        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new Exception("password not match");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

}

