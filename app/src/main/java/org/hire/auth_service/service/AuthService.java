package org.hire.auth_service.service;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import org.hire.auth_service.model.AuthEntity;
import org.hire.auth_service.repository.AuthRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.Claims;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;


@Service
public class AuthService {
    
    private final AuthRepository authRepository;
    private final String jwtSecret;
    private final long jwtTtlMillis;



    public AuthService(
            AuthRepository authRepository,
            @Value("${jwt.secret}") String jwtSecret,
            @Value("${jwt.ttl-ms}") long jwtTtlMillis) {
        this.authRepository = authRepository;
        this.jwtSecret = jwtSecret;
        this.jwtTtlMillis = jwtTtlMillis;
    }

    public static String createJWT(String id, String issuer, String subject, long ttlMills, String key){
        SignatureAlgorithm sigAlgo = SignatureAlgorithm.HS256;

        long nowMills = System.currentTimeMillis();
        Date now = new Date(nowMills);

        byte[] apiKeySecretBytes = Decoders.BASE64.decode(key);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, sigAlgo.getJcaName());


        JwtBuilder builder = Jwts.builder().setId(id)
            .setIssuedAt(now)
            .setSubject(subject)
            .setIssuer(issuer)
            .signWith(sigAlgo, signingKey);

        if(ttlMills > 0){
            long expMills = nowMills+ttlMills;
            Date exp = new Date(expMills);
            builder.setExpiration(exp);
        }

        return builder.compact();
    }

    public void registerUser(Long userId, String password){
        String hashedPassword = PasswordHasher.hashPassword(password);
        AuthEntity authEntity = new AuthEntity();
        authEntity.setUserId(userId);
        authEntity.setPassword(hashedPassword);
        authRepository.save(authEntity);
    }

    public String login(Long userId, String password){
        AuthEntity authEntity = authRepository.findByUserId(userId);
        if(authEntity == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        if(!PasswordHasher.verifyPassword(password, authEntity.getPassword())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid password");
        }

        return createJWT(
                String.valueOf(authEntity.getUserId()),
                "hire-app",
                String.valueOf(authEntity.getUserId()),
                jwtTtlMillis,
                jwtSecret
        );
    }

    public String parseAndValidateSubject(String token) {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        SecretKey signingKey = new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
}
