package com.parksmart.config;
import java.nio.charset.StandardCharsets; import java.util.Date; import org.springframework.beans.factory.annotation.Value; import org.springframework.stereotype.Component; import io.jsonwebtoken.*; import io.jsonwebtoken.security.Keys; import javax.crypto.SecretKey;
@Component public class JwtUtil {
    private final SecretKey key; private final long expiration;
    public JwtUtil(@Value("${parksmart.jwt.secret}") String secret,@Value("${parksmart.jwt.expiration-ms}") long expiration){key=Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));this.expiration=expiration;}
    public String generate(String email){Date now=new Date();return Jwts.builder().subject(email).issuedAt(now).expiration(new Date(now.getTime()+expiration)).signWith(key).compact();}
    public String subject(String token){return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject();}
}