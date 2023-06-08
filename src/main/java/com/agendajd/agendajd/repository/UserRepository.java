package com.agendajd.agendajd.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agendajd.agendajd.models.UserModel;



public interface UserRepository extends JpaRepository<UserModel, UUID> {

    Optional<UserModel> findByUsername(String username);
    Optional<UserModel> findByEmail(String email);
    
}
