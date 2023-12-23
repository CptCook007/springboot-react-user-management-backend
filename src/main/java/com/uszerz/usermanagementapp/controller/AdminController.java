package com.uszerz.usermanagementapp.controller;

import com.uszerz.usermanagementapp.entity.User;
import com.uszerz.usermanagementapp.model.dto.UserDto;
import com.uszerz.usermanagementapp.repository.UserRepository;
import com.uszerz.usermanagementapp.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin")
@Slf4j
public class AdminController {
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/home")
    public String hello(){
        return "Home";
    }

    @GetMapping("/get-users")
    public List<User> getUsers(){
        try {

            return userService.getAllUsers();
        }
        catch (Exception e){
            throw new RuntimeException("Something went wrong");
        }
    }

    @DeleteMapping("/delete-user/{userId}")
    public ResponseEntity<Map<String, Boolean>> deleteUser(@PathVariable(name = "userId") String userId){
        if(!userService.userExistsByUserId(userId)){
                return new ResponseEntity<>(Map.of("success",false),HttpStatus.BAD_REQUEST);
        }
        userService.deleteUserByUserId(userId);
        log.info("user deleted");
        return new ResponseEntity<>(Map.of("success",true), HttpStatus.OK);
    }
    @PatchMapping("/edit-user/{userId}")
    public ResponseEntity<Map<String, Boolean>> editUser(@PathVariable(name = "userId") String userId,
                                                         @RequestBody UserDto userDto){
        if(!    userService.userExistsByUserId(userId)){
            return new ResponseEntity<>(Map.of("success",false),HttpStatus.BAD_REQUEST);
        }
        if(userService.ifUsernameExists(userDto.getUsername(),userId))
            {
                return new ResponseEntity<>(Map.of("success",false),HttpStatus.CONFLICT);
            }
        userService.editUserByUserId(userId,userDto);
        log.info("user edited");
        return new ResponseEntity<>(Map.of("success",true), HttpStatus.OK);
    }
}
