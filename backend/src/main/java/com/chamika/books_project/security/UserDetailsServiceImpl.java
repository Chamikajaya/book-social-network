package com.chamika.books_project.security;

import com.chamika.books_project.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {



    private final UserRepository userRepository;

    @Override
    @Transactional // to ensure data integrity and consistency :)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)  // since we use email as the username
                .orElseThrow(() -> new UsernameNotFoundException("Could not find user with email: " + username + "."));
    }
}
