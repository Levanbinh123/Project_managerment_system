package com.example.project_management_system.repository;

import com.example.project_management_system.model.Subcripstion;
import jdk.jshell.JShell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface SubscriptionRepository extends JpaRepository<Subcripstion, Long> {
    Subcripstion findByUserId(Long userId);
    @Modifying
    @Transactional
    @Query("DELETE from Subcripstion s where s.user.id=:userId")
    void deleteByUserId(@Param("userId") Long userId);
}
