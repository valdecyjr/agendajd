package com.agendajd.agendajd.models;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class UserModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    
    @Column(nullable = false, unique = true, length = 20)
    private String username;

    @Column(nullable = false, unique = true, length = 20)
    private String email;

    @Column(nullable = false, length = 20)
    private String Fullname;

    @Column(nullable = false, length = 10)
    private LocalDate dateBirth;

    @OneToMany(mappedBy = "userContant",  cascade = CascadeType.ALL)
    private List<ContatosModel> contatos;

    @OneToMany(mappedBy = "userEvent",  cascade = CascadeType.ALL)
    private List<EventosModel> eventos;
}
