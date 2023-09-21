package com.example.discordBackend.security;

import com.example.discordBackend.configurations.EnvConfig;
import com.example.discordBackend.exception.DiscordException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {
    private EnvConfig envConfig;
    private String jwtSecret;
    private long jwtExpirationDate;

    public JwtTokenProvider(EnvConfig envConfig) {
        this.envConfig = envConfig;
        this.jwtSecret = envConfig.getJwtSecret();
        this.jwtExpirationDate = envConfig.getJwtExpirationDate();
    }

    private Key key(){
        return Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(jwtSecret)
        );
    }

    // generate jwt token
    public String generateToken(Authentication authentication){
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);

        String jwtToken = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(currentDate)
                .setExpiration(expireDate)
                .signWith(key())
                .compact();

        return jwtToken;
    }

    public String getUsername(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody();

        String username = claims.getSubject();
        return username;
    }

    public boolean validateToken(String token){
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parse(token);
            return true;
        } catch (ExpiredJwtException e) {
            throw new DiscordException(HttpStatus.BAD_REQUEST, "Expired jwt token");
        } catch (MalformedJwtException e) {
            throw new DiscordException(HttpStatus.BAD_REQUEST, "Invalid jwt token");
        } catch (UnsupportedJwtException e) {
            throw new DiscordException(HttpStatus.BAD_REQUEST, "Unsupported jwt token");
        } catch (IllegalArgumentException e) {
            throw new DiscordException(HttpStatus.BAD_REQUEST, "jwt claims string is empty");
        }
    }
}
