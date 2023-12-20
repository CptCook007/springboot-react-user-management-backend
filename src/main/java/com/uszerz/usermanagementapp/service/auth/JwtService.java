package com.uszerz.usermanagementapp.service.auth;

import com.uszerz.usermanagementapp.constants.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {
    public String generateToken(Authentication authentication){
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expirationTime = new Date(currentDate.getTime() + Constants.JWT_EXPIRATION);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(currentDate)
                .setExpiration(expirationTime)
                .signWith(SignatureAlgorithm.HS256,Constants.JWT_SECRET)
                .compact();
    }
    public String getUsernameFromToken(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(Constants.JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();
        return  claims.getSubject();
    }
    public boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(Constants.JWT_SECRET).parseClaimsJws(token);
            return true;
        }
        catch (Exception e){
            throw new AuthenticationCredentialsNotFoundException("Jwt token is expired or is invalid");
        }

    }

}
