package com.example.project_management_system.service.impl;

import com.example.project_management_system.model.Subcripstion;
import com.example.project_management_system.model.User;
import com.example.project_management_system.model.enum_.PlanType;
import com.example.project_management_system.repository.SubscriptionRepository;
import com.example.project_management_system.service.service.SubcriptionService;
import com.example.project_management_system.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class SubcriptionServiceImpl implements SubcriptionService {
    @Autowired
private UserService userService;
    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Override
    public Subcripstion createSubscription(User user) {
        Subcripstion subscription = new Subcripstion();
        subscription.setUser(user);
        subscription.setSubcriptionDateStart(LocalDate.now());
        subscription.setSubcriptionDateEnd(LocalDate.now().plusMonths(12));
        subscription.setValid(true);
        subscription.setPlanType(PlanType.FREE);
        return subscriptionRepository.save(subscription);

    }

    @Override
    public Subcripstion getUserSubcription(Long userId) throws Exception {
        Subcripstion subcripstion=subscriptionRepository.findByUserId(userId);
    if(!isValid(subcripstion)){
        subcripstion.setPlanType(PlanType.FREE);
        subcripstion.setSubcriptionDateEnd(LocalDate.now().plusMonths(12));
        subcripstion.setSubcriptionDateStart(LocalDate.now());
    }
    return subscriptionRepository.save(subcripstion);
    }

    @Override
    public Subcripstion upgradeSubcription(Long userId, PlanType planType) throws Exception {
        Subcripstion subcripstion=subscriptionRepository.findByUserId(userId);
        subcripstion.setPlanType(planType);
        subcripstion.setSubcriptionDateEnd(LocalDate.now());
        if(planType.equals(PlanType.ANNUALLY)){
            subcripstion.setSubcriptionDateEnd(LocalDate.now().plusMonths(12));
        }else {
            subcripstion.setSubcriptionDateEnd(LocalDate.now().plusMonths(1));
        }
        return subscriptionRepository.save(subcripstion);
    }

    @Override
    public boolean isValid(Subcripstion subcripstion) {
        if(subcripstion.getPlanType().equals(PlanType.FREE)){
            return true;
        }
        LocalDate endDate=subcripstion.getSubcriptionDateEnd();
        LocalDate currentDate=LocalDate.now();
        return endDate.isAfter(currentDate)||endDate.isEqual(currentDate);
    }
}
