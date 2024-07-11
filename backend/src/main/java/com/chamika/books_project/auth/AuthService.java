package com.chamika.books_project.auth;

import com.chamika.books_project.emails.EmailService;
import com.chamika.books_project.emails.EmailTemplateName;
import com.chamika.books_project.exceptions.BadRequestException;
import com.chamika.books_project.exceptions.ResourceNotFoundException;
import com.chamika.books_project.exceptions.EmailAlreadyTakenException;
import com.chamika.books_project.role.RoleRepository;
import com.chamika.books_project.security.JwtUtil;
import com.chamika.books_project.token.Token;
import com.chamika.books_project.token.TokenRepository;
import com.chamika.books_project.user.User;
import com.chamika.books_project.user.UserRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final TokenRepository tokenRepository;

    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;


    @Value("${application.mailing.frontend.activation-url}")
    private String activationUrl;


    public void registerUser(RegisterRequestBody registerRequestBody) throws MessagingException {

        // check whether the email is already registered
        if (userRepository.existsByEmail(registerRequestBody.email())) {
            throw new EmailAlreadyTakenException("Email is already taken");
        }

        User user = User.builder()
                .firstName(registerRequestBody.firstName())
                .lastName(registerRequestBody.lastName())
                .email(registerRequestBody.email())
                .password(passwordEncoder.encode(registerRequestBody.password()))  // hashing using bcrypt
                .roles(roleRepository.findByRoleName("USER").stream().collect(Collectors.toList()))
                .isAccountLocked(false)
                .isEnabled(false)  // since user has not verified the email
                .build();

        userRepository.save(user);

        // send verification email to the user
        sendVerificationEmail(user);
    }

    private void sendVerificationEmail(User user) throws MessagingException {
        String verificationToken = generateAndSaveVerificationTokenToDb(user);
        emailService.sendEmail(
                user.getEmail(),
                user.getFullName(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                activationUrl,
                verificationToken,
                "Activate your account"
        );
    }


    private String generateAndSaveVerificationTokenToDb(User user) {

        final int ACTIVATION_TOKEN_LENGTH = 6;
        final int ACTIVATION_TOKEN_EXPIRATION_MINUTES = 15;

        String charSequence = "0123456789";

        StringBuilder stringBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();

        for (int i = 0; i < ACTIVATION_TOKEN_LENGTH; i++) {
            int randomIndex = secureRandom.nextInt(charSequence.length());
            stringBuilder.append(charSequence.charAt(randomIndex));
        }

        String generatedToken = stringBuilder.toString();

        Token token = Token.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(ACTIVATION_TOKEN_EXPIRATION_MINUTES))
                .validatedAt(null)  // not validated yet
                .user(user)
                .build();

        tokenRepository.save(token);

        return generatedToken;


    }


    public AuthResponseBody login(AuthRequestBody authRequestBody) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequestBody.email(),
                        authRequestBody.password()
                )
        );

        var claims = new HashMap<String, Object>();

        // getPrincipal() returns a Object type, so we need to cast it to Customer
        User user = (User) authentication.getPrincipal();

        claims.put("username", user.getEmail());

        String jwtToken = jwtUtil.generateToken(claims, user);

        return new AuthResponseBody(jwtToken);


    }

    public void verifyEmail(String token) throws MessagingException {

        // first checking whether the token is valid
        Token tokenFromDb = tokenRepository.findByToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid activation token"));

        // checking whether the token has been validated already - otherwise the user can validate the token multiple times
        if (tokenFromDb.getValidatedAt() != null) {
            throw new BadRequestException("Token has been already validated.");
        }

        // if the token is expired
        if (LocalDateTime.now().isAfter(tokenFromDb.getExpiresAt())) {
            sendVerificationEmail(tokenFromDb.getUser());
            throw new RuntimeException("Current activation token is expired. A new token has been sent to your email");
        }

        User user = userRepository.findById(tokenFromDb.getUser().getId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // set the user as enabled
        user.setIsEnabled(true);

        tokenFromDb.setValidatedAt(LocalDateTime.now());

        // saving the user and the token
        userRepository.save(user);
        tokenRepository.save(tokenFromDb);


    }
}
