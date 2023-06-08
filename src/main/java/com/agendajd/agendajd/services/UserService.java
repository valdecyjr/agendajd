package com.agendajd.agendajd.services;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agendajd.agendajd.models.UserModel;
import com.agendajd.agendajd.repository.UserRepository;

@Service
public class UserService {
    
    private final UserRepository userRepository;

    UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserModel save(UserModel userModel) {
        return userRepository.save(userModel);
    }

    public Optional<UserModel> findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public Optional<UserModel> findByEmail(String email){
        return userRepository.findByEmail(email);
    }
}
