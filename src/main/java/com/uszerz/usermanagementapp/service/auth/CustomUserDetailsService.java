package com.uszerz.usermanagementapp.service.auth;

import com.uszerz.usermanagementapp.auth.CustomUserDetails;
import com.uszerz.usermanagementapp.entity.User;
import com.uszerz.usermanagementapp.repository.UserRepository;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(!userRepository.existsByUsername(username))
            throw new UsernameNotFoundException("Username not found");
        User user = userRepository.findByUsername(username);
        return new CustomUserDetails(user);
    }
}
