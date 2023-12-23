package com.uszerz.usermanagementapp.entity;

import com.uszerz.usermanagementapp.entity.enums.Roles;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    private String id;
    private String username;
    private String password;
    private Roles role;
    private String profileImageUrl;
}
