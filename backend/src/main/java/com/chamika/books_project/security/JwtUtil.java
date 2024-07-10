package com.chamika.books_project.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {

    @Value("${application.security.jwt.secret-key}")
    private long jwtExpirationTime;

    @Value("${application.security.jwt.expiration-time}")
    private String secretKey;  //  for signing and verifying JWTs


    // convenience method that generates a token with no extra claims.
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }


    // convenience method that generates a token with extra claims.
    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        return buildToken(extraClaims, userDetails, jwtExpirationTime);
    }


    private String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long jwtExpirationTime
    ) {

        // get the roles of the user
        var authorities = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();


        return Jwts.builder()
                .claims(extraClaims)  // set up any extra claims
                .subject(userDetails.getUsername())  // subject in the token will be the username - email
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtExpirationTime))
                .claim("authorities", authorities)  // * sets a custom claim "authorities" with the roles of the user
                .signWith(getSigningKey())
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        // check for expiration and if the user in the token matches the user in the UserDetails

        final String userNameFromToken = extractUsername(token);
        return userNameFromToken.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) {
        // Extracts the expiration claim and compares it to the current time.
        return extractAllClaims(token, Claims::getExpiration).before(new Date());  // refer the builder above

    }

    public String extractUsername(String token) {
        return extractAllClaims(token, Claims::getSubject);  // refer the builder above
    }

    public <T> T extractAllClaims(String token, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(
                Jwts.parser()
                        .verifyWith(getSigningKey())  // ? TOTO: check
                        .build()
                        .parseSignedClaims(token)
                        .getPayload()
        );
    }


    private SecretKey getSigningKey() {  // ?  changed from Key --> SecretKey
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }


}
