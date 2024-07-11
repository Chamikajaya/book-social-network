package com.chamika.books_project.auth;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequestBody registerRequestBody) throws MessagingException {

        // ! TODO: set the auth token header here or in verifyEmail method ???
        authService.registerUser(registerRequestBody);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseBody> login(@RequestBody @Valid AuthRequestBody authRequestBody) {

        AuthResponseBody authResponseBody = authService.login(authRequestBody);
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, authResponseBody.jwtToken())  // * setting the JWT token in the response header
                .body(authResponseBody);
    }


}
