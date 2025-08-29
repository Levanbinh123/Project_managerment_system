package com.example.project_management_system.service.service;

import com.example.project_management_system.model.Subcripstion;
import com.example.project_management_system.model.User;
import com.example.project_management_system.model.enum_.PlanType;

public interface SubcriptionService {
    Subcripstion createSubscription(User user);
    Subcripstion getUserSubcription(Long userId) throws Exception;

    Subcripstion upgradeSubcription(Long userId, PlanType planType) throws Exception;
    boolean isValid(Subcripstion subcripstion);
}
