package com.uszerz.usermanagementapp.controller;

import com.uszerz.usermanagementapp.entity.User;
import com.uszerz.usermanagementapp.model.dto.UserDto;
import com.uszerz.usermanagementapp.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/get-user-info/{username}")
    public ResponseEntity<UserDto> getUserInfo(@PathVariable(name = "username") String username){
        if(!userService.ifUsernameExists(username)){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
        User user = userService.getUserByUsername(username);
        UserDto userDto = new UserDto(user);
        return new ResponseEntity<>(userDto, HttpStatus.OK);

    }
}
