package edu.step.examenJavaRest.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenServiceImpl implements JwtTokenService{

    @Value("${jwt.token.secret}")
    private String secret;

    @Value("${jwt.token.validity}")
    private long tokenValidity;
    @Override
        public String generateJwtToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tokenValidity))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    @Override
    public boolean validateJwtToken(String jwtToken) {
        Claims body = Jwts.parser().setSigningKey(secret).parseClaimsJws(jwtToken).getBody();
        String subject = body.getSubject();
        Date expiration = body.getExpiration();
        return subject != null && expiration.after(new Date());
    }

    @Override
    public String getUsername(String jwtToken) {
        if(validateJwtToken(jwtToken)){
            Claims body = Jwts.parser().setSigningKey(secret).parseClaimsJws(jwtToken).getBody();
            return body.getSubject();
        }
        return null;
    }
}
