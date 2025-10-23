package com.example.project_management_system.service.impl;

import com.example.project_management_system.config.JwtProvider;
import com.example.project_management_system.model.User;
import com.example.project_management_system.repository.UserRepository;
import com.example.project_management_system.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
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
    public void deletedUser(Long userId) throws Exception {
        userRepository.deleteById(userId);
    }

    @Override
    public User updatedUser(Long userId, User user) throws Exception {
        User updatedUser = userRepository.findById(userId).orElse(null);
        if (updatedUser == null) {
            throw new Exception("user not found");
        }
        updatedUser.setEmail(user.getEmail());
        updatedUser.setRole(user.getRole());
        updatedUser.setPassword(user.getPassword());
        updatedUser.setFullName(user.getFullName());
        User userUpdated = userRepository.save(updatedUser);
        return userUpdated;
    }

    @Override
    public List<User> findAllUser() throws Exception {
        return userRepository.findAll();
    }
}
