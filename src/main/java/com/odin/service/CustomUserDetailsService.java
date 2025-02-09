package com.odin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.odin.model.User;
import com.odin.repository.UserRepository;

/**
 * Custom implementation of Spring Security's UserDetailsService.
 * Provides user authentication and authorization details.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {
    
    /**
     * Default constructor
     */
    public CustomUserDetailsService() {
        // Default constructor
    }

    @Autowired
    private UserRepository userRepository;

    /**
     * Loads user details by username (email) for authentication
     * @param username Email address of the user
     * @return UserDetails object for Spring Security
     * @throws UsernameNotFoundException if user not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null)
        {
            throw new UsernameNotFoundException(username);
        }
        List<GrantedAuthority> authorityList = new ArrayList<>();
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(), authorityList);
    }
}
