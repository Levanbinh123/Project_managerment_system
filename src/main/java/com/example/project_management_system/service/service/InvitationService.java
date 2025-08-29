package com.example.project_management_system.service.service;

import com.example.project_management_system.model.Invitation;
import com.example.project_management_system.model.User;

public interface InvitationService {
    public void sendInviation(String email, Long projectId) throws Exception;
    public Invitation accepInvitation(String token, Long UserId) throws Exception;
    public String getTokenByUserEmail(String userEmail);
    void deleteToken(String token);
}
