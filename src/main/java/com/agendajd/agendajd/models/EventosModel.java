package com.agendajd.agendajd.models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class EventosModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    private UserModel userEvent;

    @Column(nullable = false, length = 50)
    private String nameEvent;

    @Column(nullable = true, length = 50)
    private String localEvent;

    @Column(nullable = false, length = 10)
    private LocalDate dateEvent;

    @Column(nullable = true, length = 15)
    private LocalTime hourEvent;

}
