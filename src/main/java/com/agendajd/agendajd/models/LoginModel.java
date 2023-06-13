package com.agendajd.agendajd.models;

import java.time.LocalDateTime;
import java.util.UUID;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class LoginModel{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @OneToOne
    @JoinColumn(nullable = false, unique = true)
    private UserModel user;
    
    @Column(length = 100)
    private String password;

    @Column(nullable = false)
    private LocalDateTime registrationDate;
}
