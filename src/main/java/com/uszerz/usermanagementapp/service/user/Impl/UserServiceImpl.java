package com.uszerz.usermanagementapp.service.user.Impl;

import com.uszerz.usermanagementapp.entity.User;
import com.uszerz.usermanagementapp.entity.enums.Roles;
import com.uszerz.usermanagementapp.model.dto.UserDto;
import com.uszerz.usermanagementapp.repository.UserRepository;
import com.uszerz.usermanagementapp.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

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
    @Transactional
    public User registerUser(UserDto userDto){
        try {

            User user = new User();
            user.setUsername(userDto.getUsername());
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            user.setProfileImageUrl(userDto.getProfileImageUrl());
            if (userRepository.count() == 0) {
                user.setRole(Roles.ROLE_ADMIN);
            } else {
                user.setRole(Roles.ROLE_USER);
            }
            return userRepository.save(user);
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean ifUsernameExists(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findByRoleNot(Roles.ROLE_ADMIN);
    }

    @Override
    public User getUserByUsername(String username) {
        try {
            return userRepository.findByUsername(username);
        }
        catch (Exception e){
            throw  new RuntimeException(e);
        }
    }

    @Override
    public boolean userExistsByUserId(String userId) {
        return userRepository.existsById(userId);
    }

    @Override
    public void deleteUserByUserId(String userId) {
        try {
            userRepository.deleteById(userId);
        }
        catch (Exception e){
            throw  new RuntimeException(e);
        }
    }

    @Override
    public void editUserByUserId(String userId, UserDto userDto) {
        try {
            User user = userRepository.findById(userId).get();
            String newProfileImage = userDto.getProfileImageUrl();
            String newUsername = userDto.getUsername();
            if(!newUsername.isBlank()){
                user.setUsername(newUsername);
            }
            if(Objects.nonNull(newProfileImage)&&!newProfileImage.isBlank()){
                user.setProfileImageUrl(newProfileImage);
            }
            else{
                if(newProfileImage==null)
                    user.setProfileImageUrl("");
            }
            userRepository.save(user);
        }
        catch (Exception e){
            throw  new RuntimeException(e);
        }
    }

    @Override
    public boolean ifUsernameExists(String username, String userId) {
        User user = userRepository.findById(userId).get();
        log.info("exists? : "+ (ifUsernameExists(username) && !Objects.equals(user.getUsername(), username)));
        return ifUsernameExists(username) && !Objects.equals(user.getUsername(), username);
    }
}
