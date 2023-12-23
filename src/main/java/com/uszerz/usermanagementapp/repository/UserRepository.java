package com.uszerz.usermanagementapp.repository;

import com.uszerz.usermanagementapp.entity.User;
import com.uszerz.usermanagementapp.entity.enums.Roles;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User,String> {
    User findByUsername(String username);

    boolean existsByUsername(String username);

    List<User> findByRoleNot(Roles role);;
}
