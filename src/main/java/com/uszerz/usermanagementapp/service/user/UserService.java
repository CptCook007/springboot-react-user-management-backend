package com.uszerz.usermanagementapp.service.user;

import com.uszerz.usermanagementapp.entity.User;
import com.uszerz.usermanagementapp.model.dto.UserDto;
import org.bson.types.ObjectId;

import java.util.List;

public interface UserService {
     User registerUser(UserDto userDto);

    boolean ifUsernameExists(String username);

    List<User> getAllUsers();

    User getUserByUsername(String username);

    boolean userExistsByUserId(String userId);

    void deleteUserByUserId(String userId);

    void editUserByUserId(String userId, UserDto userDto);

    boolean ifUsernameExists(String username, String userId);
}
