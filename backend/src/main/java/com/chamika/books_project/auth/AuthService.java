package com.chamika.books_project.auth;

import com.chamika.books_project.role.RoleRepository;
import com.chamika.books_project.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;


    public void registerUser(RegisterRequestBody registerRequestBody) {
        // register user
    }

}
