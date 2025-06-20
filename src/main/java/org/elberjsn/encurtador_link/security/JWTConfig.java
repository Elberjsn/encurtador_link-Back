package org.elberjsn.encurtador_link.security;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.elberjsn.encurtador_link.model.User;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

@Service
public class JWTConfig {
    private static final String SECRET_KEY = "4Z^XrroxR@dWxqf$mTTKwW$!@#qGr4P";

    private static final String ISSUER = "pizzurg-api";

    public static String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withIssuedAt(creationDate())
                    .withExpiresAt(expirationDate())
                    .withSubject(user.getUsername())
                    .sign(algorithm);

        } catch (JWTCreationException exception) {
            throw new JWTCreationException("Erro ao criar o token JWT",exception);
        }
    }

    public static String getSubjectFromToken(String token){

        if (token == null || token.isEmpty() || isTokenValid(token) == false) {
            return null;
        }
        try{
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            return JWT.require(algorithm)
            .withIssuer(ISSUER)
            .build()
            .verify(token)
            .getSubject();
        }catch(JWTVerificationException exception){
           return null;
        }
    }
    public static boolean isTokenValid(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token);
            return true;
        } catch (JWTVerificationException exception) {
            return false;
        }
    }
   

    private static Instant creationDate() {
        return ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).toInstant();
    }

    private static Instant expirationDate() {
        return ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).plusHours(5).toInstant();
    }
    

}
