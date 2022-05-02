package com.docnow.docnow.auth.jwt;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

public class JwtUtil {
    @Value("${docnow.jwtSecretKey}")
    private String SECRET_KEY;
    @Value("${docnow.jwtExpirationtime}")
    private int EXPIRATION_TIME_MS;

    public String generateUsernameFromToken(String jwtToken){
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(jwtToken).getBody();
        return claims.getSubject();
    }

    public Boolean verifyJWT(String jwtToken){
        try{
            Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                    .parseClaimsJws(jwtToken).getBody().getSubject();
            return true;
        } catch(SignatureException | ExpiredJwtException |MalformedJwtException|UnsupportedJwtException|IllegalArgumentException e){
            e.printStackTrace();
        }
        return false;
    }

    public String generateJwt(Authentication authentication) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        User user = (User) authentication.getPrincipal();

        long dateNow = new Date().getTime();
        long expirationDateInms = dateNow+EXPIRATION_TIME_MS;
        byte[] apiKeyBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(apiKeyBytes,signatureAlgorithm.getJcaName());

        JwtBuilder jwtBuilder = Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(expirationDateInms))
                .signWith(signatureAlgorithm,signingKey);
        return jwtBuilder.compact();
    }
}
