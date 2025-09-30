package com.Bicycle.BicycleInventory.security;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    private static final String SECRET_KEY = "jwt.secret=MiClaveSuperSeguraDeAlMenos32Caracteres!!\r\n" + //
                "";
    private static final long EXPIRATION_TIME = 86400000; // 1 día en milisegundos
    private static final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    public static String generateToken(String user) {
        return Jwts.builder()
                .setSubject(user)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public static boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token);
            return true;
        // Lógica para generar un token JWT utilizando la información del usuario
} catch (Exception e) {
            return false;
        }
    }
    
}// Lógica para generar un token JWT utilizando la información del usuario