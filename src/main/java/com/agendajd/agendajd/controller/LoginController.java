package com.agendajd.agendajd.controller;

import com.agendajd.agendajd.dtos.CadastroDto;
import com.agendajd.agendajd.dtos.LoginDto;
import com.agendajd.agendajd.models.LoginModel;
import com.agendajd.agendajd.models.UserModel;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agendajd.agendajd.services.LoginService;
import com.agendajd.agendajd.services.UserService;

import jakarta.validation.Valid;

@CrossOrigin("*")
@RestController
@RequestMapping("/login")
public class LoginController {
    
    final UserService userService;
    final LoginService loginService;
    final PasswordEncoder encoder;

    public LoginController(UserService userService, LoginService loginService, PasswordEncoder encoder){
        this.userService = userService;
        this.loginService = loginService;
        this.encoder = encoder;
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
        loginModel.setPassword(encoder.encode(cadastro.getPassword()));
        loginModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
        loginService.save(loginModel);

        return ResponseEntity.status(HttpStatus.OK).body("Cadastrado com Sucesso");
    }

    @PostMapping("/loginvalidation")
    public ResponseEntity<Object> loginValidation(@RequestBody LoginDto user){
        Optional<LoginModel> loginOptional = loginService.findLogin(user.getUsername());
        System.out.println(loginOptional);
        if (loginOptional.isEmpty()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username não cadastrado");

        var loginModel = loginOptional.get();

        if (encoder.matches(user.getPassword(), loginModel.getPassword())){
            return ResponseEntity.status(HttpStatus.OK).body(loginModel.getUser());
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Senha incorreta");
        } 
    }
}
