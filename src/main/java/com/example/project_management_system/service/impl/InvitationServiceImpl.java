package com.example.project_management_system.service.impl;

import com.example.project_management_system.model.Invitation;
import com.example.project_management_system.repository.InvitationRepository;
import com.example.project_management_system.service.service.EmailService;
import com.example.project_management_system.service.service.InvitationService;
import com.example.project_management_system.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InvitationServiceImpl implements InvitationService {
    @Autowired
    private InvitationRepository invitationRepository;
    @Autowired
    private EmailService emailService;
    @Override
    public void sendInviation(String email, Long projectId) throws Exception {
        String invitationToken= UUID.randomUUID().toString();
        Invitation invitation = new Invitation();
        invitation.setProjectId(projectId);
        invitation.setEmail(email);
        invitation.setToken(invitationToken);
        invitationRepository.save(invitation);
        String invitationLink="http://localhost:5173/accept_invitation="+invitationToken;
        emailService.sendEmailWithToken(email,invitationLink);
    }

    @Override
    public Invitation accepInvitation(String token, Long UserId) throws Exception {
        Invitation invitation = invitationRepository.findByToken(token);
        if(invitation==null){
            throw new Exception("invatation not found!!");
        }
        return invitation;
    }

    @Override
    public String getTokenByUserEmail(String userEmail) {
        Invitation invitation = invitationRepository.findByEmail(userEmail);
        return invitation.getToken();
    }

    @Override
    public void deleteToken(String token) {
        Invitation invitation = invitationRepository.findByToken(token);
        invitationRepository.delete(invitation);
    }
}
