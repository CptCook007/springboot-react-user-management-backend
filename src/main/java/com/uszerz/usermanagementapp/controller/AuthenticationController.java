package com.uszerz.usermanagementapp.controller;

import com.uszerz.usermanagementapp.model.dto.AuthResponseDto;
import com.uszerz.usermanagementapp.model.dto.UserDto;
import com.uszerz.usermanagementapp.service.auth.JwtService;
import com.uszerz.usermanagementapp.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthenticationController {
    private final UserService userService;
    private final  AuthenticationManager authenticationManager;

    private final JwtService jwtService;
    @Autowired
    public AuthenticationController(UserService userService,AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @GetMapping("/check")
    public String check(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDto userDto){
        if(userService.ifUsernameExists(userDto.getUsername())){
            return new ResponseEntity<>("User already exists!",HttpStatus.CONFLICT);
        }
        userService.registerUser(userDto);
        return new ResponseEntity<>("User created!",HttpStatus.CREATED);
    }


    @PostMapping(value= "/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody UserDto userDto){
        Authentication authentication = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(userDto.getUsername(),userDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtService.generateToken(authentication);
        return new ResponseEntity<>(new AuthResponseDto(token),HttpStatus.OK);
    }
}
