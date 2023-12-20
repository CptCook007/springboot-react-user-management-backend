package com.uszerz.usermanagementapp.repository;

import com.uszerz.usermanagementapp.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User,Long> {
    User findByUsername(String username);

    boolean existsByUsername(String username);
}
