package com.api.apijwt.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret:secret-key}") // Pode colocar a chave secreta no application.properties
    private String secret;

    // Duração do token em milissegundos (exemplo 1 hora)
    private static final long JWT_EXPIRATION_MS = 1000 * 60 * 60;

    // Gera token JWT com username e role
    public String generateToken(String username, String role) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION_MS);

        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Extrai username do token
    public String extractUsername(String token) {
        return parseClaims(token).getSubject();
    }

    // Extrai role do token (se quiser usar depois)
    public String extractRole(String token) {
        return (String) parseClaims(token).get("role");
    }

    // Valida se token é válido e não expirou para um usuário
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // Verifica se token expirou
    public boolean isTokenExpired(String token) {
        final Date expiration = parseClaims(token).getExpiration();
        return expiration.before(new Date());
    }

    // Valida token genericamente, retornando true/false
    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // Carrega UserDetails para autenticação no filtro (exemplo básico)
    public UserDetails loadUserByUsername(String username) {
        // Aqui você pode buscar usuário no banco e suas roles, por enquanto fixa "USER"
        return User.builder()
                .username(username)
                .password("") // não usado para validação de token
                .roles("USER")
                .build();
    }

    // Helper: parseia as claims do token JWT
    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Gera a chave secreta para assinatura com base na string 'secret'
    private Key getSigningKey() {
        byte[] keyBytes = secret.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
