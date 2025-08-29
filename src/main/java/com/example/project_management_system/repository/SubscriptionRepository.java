package com.example.project_management_system.repository;

import com.example.project_management_system.model.Subcripstion;
import jdk.jshell.JShell;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subcripstion, Long> {
    Subcripstion findByUserId(Long userId);
}
