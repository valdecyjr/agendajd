package com.agendajd.agendajd.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agendajd.agendajd.models.LoginModel;


public interface LoginRepository extends JpaRepository<LoginModel, UUID>{

    Optional<LoginModel> findByUserId(UUID id);
    
}
