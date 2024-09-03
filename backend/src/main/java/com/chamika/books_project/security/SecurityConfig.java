package com.chamika.books_project.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


// * Contains the config for security - Spring Security

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    // dependency injection
    private final AuthenticationProvider authenticationProvider;  // we are using DaoAuthenticationProvider - refer to BeansConfig.java 😊
    private final JwtAuthFilter jwtAuthFilter;


    @Bean
    // Defines a bean for the security filter chain
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors(Customizer.withDefaults())  // spring will search for CorsFilter Bean defined in BeansConfig.java and apply it
                .csrf(AbstractHttpConfigurer::disable)  // disable CSRF since we are using JWT (Cross-Site Request Forgery)
                .authorizeHttpRequests(req ->
                        req.requestMatchers(
                                        "ping",
                                        "/auth/**",
                                        // Swagger related -->
                                        "/v2/api-docs",
                                        "/v3/api-docs",
                                        "/v3/api-docs/**",
                                        "/swagger-resources",
                                        "/swagger-resources/**",
                                        "/configuration/ui",
                                        "/configuration/security",
                                        "/swagger-ui/**",
                                        "/webjars/**",
                                        "/swagger-ui.html"
                                ).permitAll()  // allow the above requests, but deny the rest
                                .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))  // do not store session state since we are using JWT
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class); // add the JWT filter before the UsernamePasswordAuthenticationFilter

        return httpSecurity.build();
    }
}
