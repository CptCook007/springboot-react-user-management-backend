package com.uszerz.usermanagementapp.service.user.Impl;

import com.uszerz.usermanagementapp.entity.User;
import com.uszerz.usermanagementapp.entity.enums.Roles;
import com.uszerz.usermanagementapp.model.dto.UserDto;
import com.uszerz.usermanagementapp.repository.UserRepository;
import com.uszerz.usermanagementapp.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User registerUser(UserDto userDto){

        User user =  new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        if(userRepository.count() == 0){
            user.setRole(Roles.ROLE_ADMIN);
        }
        else{
            user.setRole(Roles.ROLE_USER);
        }
        return userRepository.save(user);
    }

    @Override
    public boolean ifUsernameExists(String username) {
        return userRepository.existsByUsername(username);
    }
}
