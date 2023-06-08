package com.agendajd.agendajd.controller;

import com.agendajd.agendajd.dtos.CadastroDto;
import com.agendajd.agendajd.models.LoginModel;
import com.agendajd.agendajd.models.LoginValidation;
import com.agendajd.agendajd.models.UserModel;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agendajd.agendajd.services.LoginService;
import com.agendajd.agendajd.services.UserService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/login")
public class LoginController {
    
    final UserService userService;
    final LoginService loginService;

    public LoginController(UserService userService, LoginService loginService){
        this.userService = userService;
        this.loginService = loginService;
    }

    @PostMapping("/cadastro")
    public ResponseEntity<Object> cadastroValidation(@RequestBody @Valid CadastroDto cadastro){

        var userVerification = userService.findByUsername(cadastro.getUsername());

        if (userVerification.isPresent()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username ja cadastrado");

        userVerification = userService.findByEmail(cadastro.getEmail());
        
        if (userVerification.isPresent()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email ja cadastrado");

        var loginModel = new LoginModel();
        var userModel = new UserModel();
        BeanUtils.copyProperties(cadastro, userModel);
        userService.save(userModel);
        var userOptional = userService.findByUsername(cadastro.getUsername());
        loginModel.setUser(userOptional.get());
        loginModel.setPassword(cadastro.getPassword());
        loginModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
        loginService.save(loginModel);

        return ResponseEntity.status(HttpStatus.OK).body("Cadastrado com Sucesso");
    }

    @PostMapping("/loginvalidation")
    public ResponseEntity<Object> loginValidation(@RequestBody LoginValidation user){

        var loginOptional = loginService.findByUsername(user.getUsername());

        if (!loginOptional.isPresent()) loginOptional = loginService.findByEmail(user.getUsername());
        if (!loginOptional.isPresent()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username não cadastrado");

        var loginModel = loginOptional.get();

        if (loginModel.getPassword().equals(user.getPassword())){
            return ResponseEntity.status(HttpStatus.OK).body(loginModel.getUser());
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Senha incorreta");
        } 
    }
}
