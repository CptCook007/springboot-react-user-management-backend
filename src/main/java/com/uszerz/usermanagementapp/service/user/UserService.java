package com.uszerz.usermanagementapp.service.user;

import com.uszerz.usermanagementapp.entity.User;
import com.uszerz.usermanagementapp.model.dto.UserDto;

public interface UserService {
     User registerUser(UserDto userDto);

    boolean ifUsernameExists(String username);
}
