package com.example.serviceFinal.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

  // Create a proper 256-bit secret key from your string
  private final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(
    "servicerequestingsystem1234567890!@#$%".getBytes(StandardCharsets.UTF_8)
  );

  public String generateToken(String email, String role) {
    return Jwts.builder()
      .setSubject(email)
      .claim("role", role)
      .setIssuedAt(new Date())
      .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60*60*10)) // 10 hours
      .signWith(SECRET_KEY)
      .compact();
  }

  public boolean isTokenValid(String token) {
    try {
      return !isTokenExpired(token);
    } catch (Exception e) {
      return false;
    }
  }

  public boolean isTokenExpired(String token) {
    try {
      return extractExpiration(token).before(new Date());
    } catch (Exception e) {
      return true; // If can't parse, consider expired
    }
  }

  public Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public String extractRole(String token) {
    return extractClaim(token, claims -> claims.get("role", String.class));
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parserBuilder()
      .setSigningKey(SECRET_KEY) // Use SECRET_KEY directly here
      .build()
      .parseClaimsJws(token)
      .getBody();
  }

  // Remove or fix getSignInKey() if you're not using it elsewhere
  private SecretKey getSignInKey() {
    return SECRET_KEY; // Just return your existing key
  }
}
