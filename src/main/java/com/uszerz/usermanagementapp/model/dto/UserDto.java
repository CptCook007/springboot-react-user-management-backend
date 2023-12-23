package com.uszerz.usermanagementapp.model.dto;

import com.uszerz.usermanagementapp.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private String userId;
    private String username;
    private String password;
    private String profileImageUrl;
    public UserDto (User user){
        this.userId = user.getId();
        this.username = user.getUsername();
        this.profileImageUrl = user.getProfileImageUrl();
    }
}
