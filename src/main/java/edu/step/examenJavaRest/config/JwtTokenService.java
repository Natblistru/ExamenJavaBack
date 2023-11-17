package edu.step.examenJavaRest.config;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtTokenService {

    String generateJwtToken(UserDetails userDetails);
    boolean validateJwtToken(String token);
    String getUsername(String jwtToken);
}
