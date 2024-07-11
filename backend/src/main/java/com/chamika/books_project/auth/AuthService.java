package com.chamika.books_project.auth;

import com.chamika.books_project.emails.EmailService;
import com.chamika.books_project.emails.EmailTemplateName;
import com.chamika.books_project.exceptions.EmailAlreadyTakenException;
import com.chamika.books_project.role.RoleRepository;
import com.chamika.books_project.token.Token;
import com.chamika.books_project.token.TokenRepository;
import com.chamika.books_project.user.User;
import com.chamika.books_project.user.UserRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final TokenRepository tokenRepository;

    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

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


}
