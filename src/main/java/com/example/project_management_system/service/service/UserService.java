package com.example.project_management_system.service.service;

import com.example.project_management_system.model.User;

import java.util.List;

public interface UserService {
    User findUserProfileByJwt(String jwt) throws Exception;
    User findUserByEmail(String email) throws Exception;
    User findUserById(Long userId) throws Exception;
    User updateUsersProjectSize(User user,int number) throws Exception;
    /// admin
    void deletedUser(Long userId) throws Exception;
    User updatedUser(Long userId,User user) throws Exception;
    List<User> findAllUser() throws Exception;
}
