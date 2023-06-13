package com.agendajd.agendajd.services;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.stereotype.Service;

import com.agendajd.agendajd.models.LoginModel;
import com.agendajd.agendajd.models.UserModel;
import com.agendajd.agendajd.repository.LoginRepository;

@Service
public class LoginService {

    private final LoginRepository loginRepository;
    private final UserService userService;

    LoginService(LoginRepository loginRepository, UserService userService){
        this.loginRepository = loginRepository;
        this.userService = userService;
    }

    public LoginModel save(LoginModel loginModel) {
        return loginRepository.save(loginModel);
    }

    public List<LoginModel> findAll(){
        return loginRepository.findAll();
    }

    public Optional<LoginModel> findLogin(String username){
        Optional<UserModel> userOptional = userService.findByUsername(username);
        System.out.println(userOptional);
        if (userOptional.isPresent()){
            var userModel = userOptional.get();
            System.out.println(userModel);
            return loginRepository.findByUserId(userModel.getId());
        }
        else{
            userOptional = userService.findByEmail(username);
            if (userOptional.isPresent()){
                var userModel = userOptional.get();
                return loginRepository.findByUserId(userModel.getId());
            }
            else{
                Optional<LoginModel> loginModel = Optional.empty();
                return loginModel;
            }
        }
    }
    
}
