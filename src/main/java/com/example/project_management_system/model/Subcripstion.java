package com.example.project_management_system.model;

import com.example.project_management_system.model.enum_.PlanType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Subcripstion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private LocalDate subcriptionDateStart;
    private LocalDate subcriptionDateEnd;
    private PlanType planType;
    private boolean isValid;
    @OneToOne
    private  User user;
}
