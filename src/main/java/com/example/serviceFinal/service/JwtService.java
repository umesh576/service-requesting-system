package com.example.serviceFinal.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
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
      .setSubject(email) // Use .subject() instead of .setSubject()
      .claim("role", role)
      .setIssuedAt(new Date()) // Use new Date() for current time
      .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
      .signWith(SECRET_KEY) // Sign with the SecretKey object
      .compact();
  }
}
