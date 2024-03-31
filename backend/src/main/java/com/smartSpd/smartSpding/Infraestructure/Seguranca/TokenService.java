package com.smartSpd.smartSpding.Infraestructure.Seguranca;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.smartSpd.smartSpding.Core.Dominio.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.Set;

@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;

    private Set<String> tokenBlacklist = new HashSet<>();

    public String generateToken(User user) {
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("Smart-Spending")
                    .withSubject(user.getLogin())
                    .withExpiresAt(genExpirationDate())
                    .sign(algorithm);
            return token;

        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error while generating token", exception);
        }
    }

    public String validateToken(String token){
        try {
            /*System.out.println("Token recebido para validação: " + token);*/

            Algorithm algorithm = Algorithm.HMAC256(secret);
            String subject = JWT.require(algorithm)
                    .withIssuer("Smart-Spending")
                    .build()
                    .verify(token)
                    .getSubject();

            // Se o token estiver na lista negra, considere-o inválido
            if (tokenBlacklist.contains(token)) {
                throw new JWTVerificationException("Token in blacklist");
            }
            /*System.out.println("Login recuperado com sucesso: " + subject);*/

            return subject;
        } catch (JWTVerificationException exception){
            System.out.println("Erro na validação do token: " + exception.getMessage());

            return "";
        }
    }
    public void addToBlacklist(String token) {
        tokenBlacklist.add(token);
    }

    private Instant genExpirationDate() {
        return LocalDateTime.now().plusHours(8).toInstant(ZoneOffset.of("-03:00"));
    }

    public boolean isTokenBlacklisted(String token) {
        return tokenBlacklist.contains(token);
    }


}
