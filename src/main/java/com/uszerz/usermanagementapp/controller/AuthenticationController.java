package com.uszerz.usermanagementapp.controller;

import com.uszerz.usermanagementapp.entity.User;
import com.uszerz.usermanagementapp.model.dto.AuthResponseDto;
import com.uszerz.usermanagementapp.model.dto.LoginDto;
import com.uszerz.usermanagementapp.model.dto.UserDto;
import com.uszerz.usermanagementapp.service.auth.JwtService;
import com.uszerz.usermanagementapp.service.user.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthenticationController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    @Autowired
    public AuthenticationController(UserService userService, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @GetMapping("/check")
    public String check() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDto userDto) {

        if (userService.ifUsernameExists(userDto.getUsername())) {
            return new ResponseEntity<>("User Exists!", HttpStatus.CONFLICT);
        }
        log.info("user registered");
        userService.registerUser(userDto);
        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }


    @PostMapping(value = "/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDto loginDto, HttpServletResponse response) {
        Authentication authentication;
        try {
            authentication = authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
        }
        catch (Exception error){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtService.generateToken(authentication);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByUsername(username);
        return new ResponseEntity<>
                (new AuthResponseDto(token,user.getUsername(),user.getRole().name().replace("ROLE_","")), HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request,HttpServletResponse response) throws ServletException {
        new SecurityContextLogoutHandler().logout(request, response, null);
        return new ResponseEntity<>("Logout successful", HttpStatus.OK);
    }

    @GetMapping("/connection")
    public ResponseEntity<String> checkConnection() {
        return ResponseEntity.ok("Connection successful");
    }

}
