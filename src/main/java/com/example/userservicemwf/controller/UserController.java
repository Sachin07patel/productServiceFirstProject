package com.example.userservicemwf.controller;

import com.example.userservicemwf.dto.LoginRequestDto;
import com.example.userservicemwf.dto.LogoutRequestDto;
import com.example.userservicemwf.dto.SignUpRequestDto;
import com.example.userservicemwf.models.Token;
import com.example.userservicemwf.models.User;
import com.example.userservicemwf.services.UsersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private UsersService usersService;

    public UserController(UsersService usersService){
        this.usersService = usersService;
    }
    public Token login(){
        return null;
    }

    @PostMapping("/signup")
    public User signUp(@RequestBody SignUpRequestDto request){
        String name = request.getName();
        String email = request.getEmail();
        String password = request.getPassword();
        return usersService.signUp(name, email, password);
    }

    @PostMapping("/login")
    public Token logIn(@RequestBody LoginRequestDto request){
        Token token = usersService.logIn(request.getEmail(), request.getPassword());
        return token;
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logOut(@RequestBody LogoutRequestDto request){
        usersService.logout(request.getToken());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
